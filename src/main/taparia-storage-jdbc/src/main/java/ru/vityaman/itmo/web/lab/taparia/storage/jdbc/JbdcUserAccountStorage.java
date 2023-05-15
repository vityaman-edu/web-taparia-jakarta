package ru.vityaman.itmo.web.lab.taparia.storage.jdbc;

import ru.vityaman.itmo.web.lab.taparia.user.UserAccount;
import ru.vityaman.itmo.web.lab.taparia.storage.StorageException;
import ru.vityaman.itmo.web.lab.taparia.storage.UserAccountStorage;
import ru.vityaman.itmo.web.lab.taparia.storage.jdbc.database.Database;
import ru.vityaman.itmo.web.lab.taparia.storage.jooq.tables.records.UserAccountRecord;

import java.util.Optional;

import static org.jooq.impl.DSL.val;
import static ru.vityaman.itmo.web.lab.taparia.storage.jooq.Tables.USER_ACCOUNT;

public final class JbdcUserAccountStorage
    extends JdbcStorage
    implements UserAccountStorage {
    public JbdcUserAccountStorage(Database database) {
        super(database);
    }

    @Override
    public Optional<UserAccount> findUserById(UserAccount.Id id)
        throws StorageException {
        return withConnection(db -> db
            .selectFrom(USER_ACCOUNT)
            .where(USER_ACCOUNT.ID.equal(id.value()))
            .fetchOptional(this::toUser)
        );
    }

    @Override
    public Optional<UserAccount> findUserByName(UserAccount.Name name)
        throws StorageException {
        return withConnection(db -> db
            .selectFrom(USER_ACCOUNT)
            .where(USER_ACCOUNT.LOGIN.equal(name.value()))
            .fetchOptional(this::toUser)
        );
    }

    @Override
    public UserAccount insert(UserAccount userDraft)
        throws StorageException {
        return withConnection(db -> db
            .insertInto(
                USER_ACCOUNT,
                USER_ACCOUNT.LOGIN,
                USER_ACCOUNT.PASSWORD_HASH,
                USER_ACCOUNT.PASSWORD_SALT)
            .values(
                val(userDraft.name().value()),
                val(userDraft.password().hash()),
                val(userDraft.password().salt())
            )
            .returningResult(USER_ACCOUNT.ID)
            .fetchOptional(r -> {
                final var assignedId =
                    UserAccount.Id.of(r.get(USER_ACCOUNT.ID));
                return userDraft.withId(assignedId);
            })
            .orElseThrow(() -> new StorageException("can't insert user"))
        );
    }

    private UserAccount toUser(UserAccountRecord record) {
        return UserAccount.of(
            UserAccount.Id.of(record.getId()),
            UserAccount.Name.of(record.getLogin()),
            UserAccount.Password.fromHashAndSalt(
                record.getPasswordHash(),
                record.getPasswordSalt()
            )
        );
    }
}
