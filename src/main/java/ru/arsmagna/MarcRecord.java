package ru.arsmagna;

import org.jetbrains.annotations.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

/**
 * MARC record.
 */
public final class MarcRecord
{
    /**
     * База данных, в которой хранится запись.
     */
    public String database;

    /**
     * MFN записи.
     */
    public int mfn;

    /**
     * Статус записи.
     */
    public int status;

    /**
     * Версия записи. Нумеруется с нуля.
     */
    public int version;

    public Collection<RecordField> fields;

    /**
     * Библиографическое описание.
     */
    public String description;

    /**
     * Используется при сортировке записей.
     */
    public String sortKey;

    /**
     * Индекс документа.
     */
    public String index;

    /**
     * Произвольные пользовательские данные.
     */
    public Object userData;

    //=========================================================================

    /**
     * Конструктор по умолчанию.
     */
    public MarcRecord()
    {
        fields = new ArrayList<RecordField>();
    }

    //=========================================================================

    /**
     * Клонирование записи со всеми полями.
     * @return Копию записи.
     */
    public MarcRecord Clone()
    {
        MarcRecord result = new MarcRecord();
        result.database = database;
        result.mfn = mfn;
        result.status = status;
        result.description = description;
        result.sortKey = sortKey;
        result.index = index;
        for (RecordField field: fields)
        {
            result.fields.add(field.clone());
        }

        return result;
    }

    /**
     * Разбор строк, возвращаемых сервером.
     * @param record Запись, в которую помещать результат.
     * @param text Строки, содержащие запись.
     * @throws IOException Ошибка ввода-вывода.
     */
    public static void ParseSingle
        (
            @NotNull MarcRecord record,
            @NotNull String[] text
        )
        throws IOException
    {
        String line = text[0];
        String[] parts = line.split("#");
        record.mfn = Integer.parseInt(parts[0]);
        if (parts.length != 1)
        {
            record.status = Integer.parseInt(parts[1]);
        }
        line = text[1];
        parts = line.split("#");
        record.version = Integer.parseInt(parts[1]);
        for (int i = 2; i < text.length; i++)
        {
            RecordField field = RecordField.parse(text[i]);
            record.fields.add(field);
        }
    }

    //=========================================================================

    @NotNull
    @Override
    public String toString()
    {
        return ProtocolText.EncodeRecord(this);
    }
}
