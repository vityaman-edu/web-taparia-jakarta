package ru.vityaman.itmo.web.lab.taparia.storage.jdbc;

import org.jooq.types.YearToSecond;
import ru.vityaman.itmo.web.lab.taparia.storage.AccessTokenStorage;
import ru.vityaman.itmo.web.lab.taparia.storage.StorageException;
import ru.vityaman.itmo.web.lab.taparia.storage.jdbc.database.Database;
import ru.vityaman.itmo.web.lab.taparia.storage.jooq.tables.records.AccessTokenRecord;
import ru.vityaman.itmo.web.lab.taparia.user.UserAccessToken;
import ru.vityaman.itmo.web.lab.taparia.user.UserAccount;

import java.util.Optional;

import static ru.vityaman.itmo.web.lab.taparia.storage.jooq.tables.AccessToken.ACCESS_TOKEN;

public final class JdbcAccessTokenStorage
    extends JdbcStorage
    implements AccessTokenStorage {

    public JdbcAccessTokenStorage(Database database) {
        super(database);
    }

    @Override
    public Optional<UserAccessToken> findTokenByUserId(UserAccount.Id id)
        throws StorageException {
        return withConnection(db -> db
            .selectFrom(ACCESS_TOKEN)
            .where(ACCESS_TOKEN.USER_ID.equal(id.value()))
            .fetchOptional(this::toModel)
        );
    }

    @Override
    public void upsertToken(UserAccessToken token)
        throws StorageException {
        withConnection(db -> db
            .insertInto(ACCESS_TOKEN)
            .set(ACCESS_TOKEN.USER_ID, token.userId().value())
            .set(ACCESS_TOKEN.TOKEN, token.secret().value())
            .set(ACCESS_TOKEN.CREATED_AT, token.createdAt())
            .set(ACCESS_TOKEN.TTL, YearToSecond.valueOf(token.ttl()))
            .onDuplicateKeyUpdate()
            .set(ACCESS_TOKEN.TOKEN, token.secret().value())
            .set(ACCESS_TOKEN.CREATED_AT, token.createdAt())
            .set(ACCESS_TOKEN.TTL, YearToSecond.valueOf(token.ttl()))
            .execute()
        );
    }

    private UserAccessToken toModel(AccessTokenRecord record) {
        return UserAccessToken.of(
            UserAccount.Id.of(record.getUserId()),
            UserAccessToken.Body.of(record.getToken()),
            record.getCreatedAt(),
            record.getTtl().toDuration()
        );
    }
}
