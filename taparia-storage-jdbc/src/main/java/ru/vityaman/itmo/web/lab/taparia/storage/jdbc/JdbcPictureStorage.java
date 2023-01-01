package ru.vityaman.itmo.web.lab.taparia.storage.jdbc;

import ru.vityaman.itmo.web.lab.taparia.Picture;
import ru.vityaman.itmo.web.lab.taparia.user.UserAccount;
import ru.vityaman.itmo.web.lab.taparia.storage.PictureStorage;
import ru.vityaman.itmo.web.lab.taparia.storage.StorageException;
import ru.vityaman.itmo.web.lab.taparia.storage.jdbc.database.Database;
import ru.vityaman.itmo.web.lab.taparia.storage.jooq.tables.records.PictureRecord;

import java.util.Collection;
import java.util.Optional;

import static org.jooq.impl.DSL.val;
import static ru.vityaman.itmo.web.lab.taparia.storage.jooq.Tables.PICTURE;


public final class JdbcPictureStorage
    extends JdbcStorage
    implements PictureStorage {

    private final FigureSerializer serializer;

    public JdbcPictureStorage(
        FigureSerializer serializer,
        Database database
    ) {
        super(database);
        this.serializer = serializer;
    }

    @Override
    public Optional<Picture> findPictureById(Picture.Id id)
        throws StorageException {
        return withConnection(db -> db
            .selectFrom(PICTURE)
            .where(PICTURE.ID.equal(id.value()))
            .fetchOptional(this::toPicture)
        );
    }

    @Override
    public Collection<Picture> findAllPicturesByOwnerId(UserAccount.Id ownerId)
        throws StorageException {
        return withConnection(db -> db
            .selectFrom(PICTURE)
            .where(PICTURE.OWNER_ID.equal(ownerId.value()))
            .fetch(this::toPicture)
        );
    }

    @Override
    public Picture.Id insert(Picture pictureDraft) throws StorageException {
        return withConnection(db -> db
            .insertInto(
                PICTURE,
                PICTURE.OWNER_ID,
                PICTURE.NAME,
                PICTURE.DATA)
            .values(
                val(pictureDraft.ownerId().value()),
                val(pictureDraft.name().value()),
                val(serializer.convertToJson(pictureDraft.data().figure())))
            .returningResult(PICTURE.ID)
            .fetchOptional(r -> Picture.Id.of(r.get(PICTURE.ID)))
            .orElseThrow(() -> new StorageException("can't insert user"))
        );
    }

    private Picture toPicture(PictureRecord record) {
        return Picture.of(
            Picture.Id.of(record.getId()),
            UserAccount.Id.of(record.getOwnerId()),
            Picture.Name.of(record.getName()),
            Picture.Data.of(serializer.parseFigureFromJson(record.getData()))
        );
    }
}
