package ru.arsmagna.infrastructure;

import org.jetbrains.annotations.*;

import ru.arsmagna.*;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * Клиентский запрос.
 */
public final class ClientQuery
{
    /**
     * Разделитель строк в клиентском запросе.
     */
    public static final char DELIMITER=(char)0x0A;

    //=========================================================================

    /**
     * Код команды.
     */
    public String commandCode;

    /**
     * Код АРМ.
     */
    public char workstation;

    /**
     * Идентификатор клиента.
     */
    public int clientId;

    /**
     * Номер команды.
     */
    public int commandNumber;

    /**
     * Пользовательский логин.
     */
    public String userLogin;

    //=========================================================================

    /**
     * Конструктор.
     * @param connection Подключение.
     * @param commandCode Код команды.
     */
    public ClientQuery
        (
            @NotNull IrbisConnection connection,
            @NotNull String commandCode
        )
        throws IOException
    {
        stream = new ByteArrayOutputStream();
        addAnsi(commandCode);
        addAnsi(new Character(connection.workstation).toString());
        addAnsi(commandCode);
        add(connection.clientId);
        connection.queryId++;
        add(connection.queryId);
        addAnsi(connection.password);
        addAnsi(connection.username);
        addLineFeed();
        addLineFeed();
        addLineFeed();
    }

    //=========================================================================

    private ByteArrayOutputStream stream;

    //=========================================================================

    public final void add
        (
            boolean value
        )
        throws IOException
    {
        String text = value ? "1" : "0";
        addAnsi(text);
    }

    public final void add
        (
            int value
        )
        throws IOException
    {
        String text = new Integer(value).toString();
        addAnsi(text);
    }

    public final void addAnsi
        (
            @Nullable String text
        )
        throws IOException
    {
        addAnsiNoLF(text);
        addLineFeed();
    }

    public final void addAnsiNoLF
        (
            @Nullable String text
        )
        throws IOException
    {
        if (!Utility.isNullOrEmpty(text))
        {
            byte[] bytes = text.getBytes(IrbisEncoding.ansi());
            stream.write(bytes);
        }
    }

    public final void addLineFeed() throws IOException
    {
        stream.write(Utility.LF);
    }

    public final void addUtf
        (
            @Nullable String text
        )
        throws IOException
    {
        if (!Utility.isNullOrEmpty(text))
        {
            byte[] bytes = text.getBytes(IrbisEncoding.utf());
            stream.write(bytes);
        }
        addLineFeed();
    }

    @NotNull
    public final byte[] encode()
    {
        byte[] buffer = stream.toByteArray();
        byte[] prefix = IrbisEncoding.ansi().encode(buffer.length + "\n").array();
        byte[] result = new byte[prefix.length + buffer.length];
        System.arraycopy(prefix, 0, result, 0, prefix.length);
        System.arraycopy(buffer, 0, result, prefix.length, buffer.length);

        return result;
    }
}
