package ru.vityaman.itmo.web.lab.taparia.storage.jdbc;

import ru.vityaman.itmo.web.lab.taparia.Picture;
import ru.vityaman.itmo.web.lab.taparia.TapResult;
import ru.vityaman.itmo.web.lab.taparia.user.UserAccount;
import ru.vityaman.itmo.web.lab.taparia.figure.primitive.Point;
import ru.vityaman.itmo.web.lab.taparia.storage.StorageException;
import ru.vityaman.itmo.web.lab.taparia.storage.TapResultStorage;
import ru.vityaman.itmo.web.lab.taparia.storage.jdbc.database.Database;
import ru.vityaman.itmo.web.lab.taparia.storage.jooq.enums.TapResultStatus;
import ru.vityaman.itmo.web.lab.taparia.storage.jooq.tables.records.TapResultRecord;

import java.util.Collection;
import java.util.List;

import static org.jooq.impl.DSL.val;
import static ru.vityaman.itmo.web.lab.taparia.storage.jooq.Tables.TAP_RESULT;

public final class JdbcTapResultStorage
    extends JdbcStorage
    implements TapResultStorage {

    public JdbcTapResultStorage(Database database) {
        super(database);
    }

    @Override
    public TapResult.Id insert(TapResult draft) throws StorageException {
        return withConnection(db -> db
            .insertInto(TAP_RESULT,
                TAP_RESULT.OWNER_ID,
                TAP_RESULT.PICTURE_ID,
                TAP_RESULT.X,
                TAP_RESULT.Y,
                TAP_RESULT.STATUS)
            .values(
                val(draft.ownerId().value()),
                val(draft.pictureId().value()),
                val(draft.point().x()),
                val(draft.point().y()),
                val(TapResultStatus.lookupLiteral(draft.status().asString())))
            .returningResult(TAP_RESULT.ID)
            .fetchOptional(r -> TapResult.Id.of(r.get(TAP_RESULT.ID)))
            .orElseThrow(() -> new StorageException("can't insert tap result"))
        );
    }

    @Override
    public List<TapResult> findAllResultsOrderedByCreationTime(
        UserAccount.Id ownerId, Picture.Id pictureId
    ) throws StorageException {
        return withConnection(db -> db
            .selectFrom(TAP_RESULT)
            .where(TAP_RESULT.OWNER_ID.equal(ownerId.value()))
            .and(TAP_RESULT.PICTURE_ID.equal(pictureId.value()))
            .orderBy(TAP_RESULT.ID)
            .fetch(this::toTapResult)
        );
    }

    @Override
    public void removeAllById(Collection<TapResult.Id> ids)
        throws StorageException {
        // TODO: implement
        throw new RuntimeException("Not implemented");
    }

    private TapResult toTapResult(TapResultRecord record) {
        return TapResult.of(
            TapResult.Id.of(record.getId()),
            UserAccount.Id.of(record.getOwnerId()),
            Picture.Id.of(record.getPictureId()),
            Point.of(record.getX(), record.getY()),
            switch (record.getStatus()) {
                case HIT -> TapResult.Status.HIT;
                case MISS -> TapResult.Status.MISS;
            }
        );
    }
}
