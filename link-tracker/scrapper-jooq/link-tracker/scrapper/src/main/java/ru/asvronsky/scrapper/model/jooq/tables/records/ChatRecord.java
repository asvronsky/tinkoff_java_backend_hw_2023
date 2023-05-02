/*
 * This file is generated by jOOQ.
 */
package ru.asvronsky.scrapper.model.jooq.tables.records;


import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.Row1;
import org.jooq.impl.UpdatableRecordImpl;

import ru.asvronsky.scrapper.model.jooq.tables.Chat;


/**
 * This class is generated by jOOQ.
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class ChatRecord extends UpdatableRecordImpl<ChatRecord> implements Record1<Long> {

    private static final long serialVersionUID = 1L;

    /**
     * Setter for <code>public.chat.chat_id</code>.
     */
    public void setChatId(Long value) {
        set(0, value);
    }

    /**
     * Getter for <code>public.chat.chat_id</code>.
     */
    public Long getChatId() {
        return (Long) get(0);
    }

    // -------------------------------------------------------------------------
    // Primary key information
    // -------------------------------------------------------------------------

    @Override
    public Record1<Long> key() {
        return (Record1) super.key();
    }

    // -------------------------------------------------------------------------
    // Record1 type implementation
    // -------------------------------------------------------------------------

    @Override
    public Row1<Long> fieldsRow() {
        return (Row1) super.fieldsRow();
    }

    @Override
    public Row1<Long> valuesRow() {
        return (Row1) super.valuesRow();
    }

    @Override
    public Field<Long> field1() {
        return Chat.CHAT.CHAT_ID;
    }

    @Override
    public Long component1() {
        return getChatId();
    }

    @Override
    public Long value1() {
        return getChatId();
    }

    @Override
    public ChatRecord value1(Long value) {
        setChatId(value);
        return this;
    }

    @Override
    public ChatRecord values(Long value1) {
        value1(value1);
        return this;
    }

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    /**
     * Create a detached ChatRecord
     */
    public ChatRecord() {
        super(Chat.CHAT);
    }

    /**
     * Create a detached, initialised ChatRecord
     */
    public ChatRecord(Long chatId) {
        super(Chat.CHAT);

        setChatId(chatId);
        resetChangedOnNotNull();
    }
}
