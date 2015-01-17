package com.magic.momir.providers;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.util.SparseArray;

import com.magic.momir.datasets.CardTable;

import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.Set;

public class MomirContentProvider extends ContentProvider {
    public static final String AUTHORITY = "com.magic.momir.app.providers";
    public static final Uri BASE_URI = Uri.parse("content://" + AUTHORITY);

    private static SQLiteDatabase sDatabase;

    private final SparseArray<DatabaseSet> mMappings = new SparseArray<DatabaseSet>();
    private final UriMatcher mUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    protected SQLiteDatabase getDatabase() {
        if (sDatabase == null) {
            createDatabase();
        }
        return sDatabase;
    }

    private void createDatabase() {
        if (sDatabase == null) {
            final MomirDatabase database = new MomirDatabase(getContext(), getSets());
            sDatabase = database.getWritableDatabase();
        }
    }

    protected void destroyDatabase() {
        if (sDatabase != null) {
            sDatabase.close();
            sDatabase = null;
        }
    }

    private Collection<DatabaseSet> getSets() {
        final int size = mMappings.size();
        final Set<DatabaseSet> sets = new LinkedHashSet<>(size);
        for (int i = 0; i < size; i++) {
            sets.add(mMappings.get(mMappings.keyAt(i)));
        }
        return sets;
    }

    public static final class Uris {
        public static final Uri CARD_URI = Uri.parse(BASE_URI + "/" + Paths.CARD_TABLE);
    }

    private static final class Tables {
        private static final String CARD = CardTable.TABLE_NAME;
    }

    private static final class Views {
    }

    private static final class Paths {
        public static final String CARD_TABLE = Tables.CARD;
    }

    private static final class Codes {
        public static final int CARD = 3;
    }

    @Override
    public boolean onCreate() {
        mUriMatcher.addURI(AUTHORITY, Paths.CARD_TABLE, Codes.CARD);
        mMappings.append(Codes.CARD, new CardTable());

        createDatabase();
        return true;
    }

    @Override
    public Uri insert(final Uri uri, final ContentValues values) {
        final SQLiteDatabase insertDB = sDatabase;
        final int match = mUriMatcher.match(uri);

        final DatabaseSet databaseSet = mMappings.get(match);
        final long id = databaseSet.insert(insertDB, uri, values);

        final String idValue = String.valueOf(id);
        final Uri uriToReturn = uri.buildUpon().appendPath(idValue).build();
        return uriToReturn;
    }

    @Override
    public Cursor query(final Uri uri, final String[] projection, final String selection, final String[] selectionArgs, final String sortOrder) {
        final SQLiteDatabase queryDB = sDatabase;
        final int match = mUriMatcher.match(uri);

        final DatabaseSet databaseSet = mMappings.get(match);
        final Cursor cursor = databaseSet.query(queryDB, uri, projection, selection, selectionArgs, sortOrder);

        return cursor;
    }

    @Override
    public int delete(final Uri uri, final String selection, final String[] selectionArgs) {
        final SQLiteDatabase deleteDB = sDatabase;
        final int match = mUriMatcher.match(uri);

        final DatabaseSet databaseSet = mMappings.get(match);
        final int removedRows = databaseSet.delete(deleteDB, uri, selection, selectionArgs);

        return removedRows;
    }

    @Override
    public int update(final Uri uri, final ContentValues values, final String whereClause, final String[] whereArgs) {
        final SQLiteDatabase updateDB = sDatabase;
        final int match = mUriMatcher.match(uri);

        final DatabaseSet databaseSet = mMappings.get(match);
        final int updatedRows = databaseSet.update(updateDB, uri, values, whereClause, whereArgs);

        return updatedRows;
    }

    @Override
    public String getType(Uri uri) {
        return null;
    }
}
