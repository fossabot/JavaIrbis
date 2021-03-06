package ru.arsmagna;

import org.jetbrains.annotations.*;

/**
 * Строчка в INI-файле.
 */
public final class IniLine
{
    /**
     * Ключ.
     */
    public String key;

    /**
     * Значение.
     */
    public String value;

    //=========================================================================

    /**
     * Конструктор по умолчанию.
     */
    public IniLine()
    {
    }

    /**
     * Конструктор.
     * @param key Ключ.
     * @param value Значение.
     */
    public IniLine
        (
            @NotNull String key,
            @Nullable String value
        )
    {
        this.key = key;
        this.value = value;
    }

    //=========================================================================

    @NotNull
    @Override
    @Contract(pure = true)
    public String toString()
    {
        return key + "=" + value;
    }
}
