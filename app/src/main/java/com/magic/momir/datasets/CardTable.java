package com.magic.momir.datasets;

import android.content.ContentValues;
import android.database.Cursor;

import com.magic.momir.models.Card;
import com.magic.momir.providers.DatabaseTable;

import java.util.Map;

public class CardTable extends DatabaseTable {

    public static final String TABLE_NAME = "card_table";

    public static class Columns extends DatabaseTable.Columns{
        public static final String NAME = "name";
        public static final String CMC = "cmc";
        public static final String TYPE = "type";
        public static final String SUBTYPE = "subtypes";
        public static final String TEXT = "text";
        public static final String POWER = "power";
        public static final String TOUGHNESS = "toughness";
        public static final String MULTIVERSE_ID = "multiverse_id";
        public static final String BOARD = "player";
    }

    public static ContentValues[] getContentValues(final Card[] cards) {
        final ContentValues[] values = new ContentValues[cards.length];
        for (int i = 0; i < cards.length; i++) {
            values[i] = getContentValues(cards[i]);
        }
        return values;
    }

    public static ContentValues getContentValues(final Card card){
        ContentValues contentValues = new ContentValues();
        contentValues.put(Columns.NAME, card.getName());
        contentValues.put(Columns.CMC, card.getConvertedManaCost());
        contentValues.put(Columns.TEXT, card.getDescription());
        contentValues.put(Columns.MULTIVERSE_ID, card.getMultiverseId());
        contentValues.put(Columns.TYPE, card.getType());
        contentValues.put(Columns.SUBTYPE, card.getSubType());
        contentValues.put(Columns.POWER, card.getPower());
        contentValues.put(Columns.TOUGHNESS, card.getToughness());
        contentValues.put(Columns.BOARD, card.getBoard());
        return contentValues;
    }

    public static Card fromCursor(final Cursor cursor) {
        final Card card = new Card();
        card.setName(cursor.getString(cursor.getColumnIndex(Columns.NAME)));
        card.setConvertedManaCost(cursor.getInt(cursor.getColumnIndex(Columns.CMC)));
        card.setDescription(cursor.getString(cursor.getColumnIndex(Columns.TEXT)));
        card.setMultiverseId(cursor.getInt(cursor.getColumnIndex(Columns.MULTIVERSE_ID)));
        card.setType(cursor.getString(cursor.getColumnIndex(Columns.TYPE)));
        card.setSubType(cursor.getString(cursor.getColumnIndex(Columns.SUBTYPE)));
        card.setPower(cursor.getInt(cursor.getColumnIndex(Columns.POWER)));
        card.setToughness(cursor.getInt(cursor.getColumnIndex(Columns.TOUGHNESS)));
        return card;
    }

    @Override
    public String getName() {
        return TABLE_NAME;
    }

    @Override
    protected Map<String, String> getColumnTypes() {
        Map<String, String> columnTypes = super.getColumnTypes();
        columnTypes.put(Columns.NAME, "TEXT");
        columnTypes.put(Columns.CMC, "INTEGER");
        columnTypes.put(Columns.TEXT, "TEXT");
        columnTypes.put(Columns.MULTIVERSE_ID, "INTEGER");
        columnTypes.put(Columns.TYPE, "TEXT");
        columnTypes.put(Columns.SUBTYPE, "TEXT");
        columnTypes.put(Columns.POWER, "TEXT");
        columnTypes.put(Columns.TOUGHNESS, "TEXT");
        return columnTypes;
    }

    @Override
    protected String getConstraint() {
        return "UNIQUE (" + Columns.MULTIVERSE_ID + ") ON CONFLICT REPLACE";
    }
}
