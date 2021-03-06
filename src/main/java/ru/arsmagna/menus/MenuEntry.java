package ru.arsmagna.menus;

import org.jetbrains.annotations.*;

/**
 * Пара строк в меню.
 */
public final class MenuEntry
{
    /**
     * Код (первая строка).
     */
    public String code;

    /**
     * Комментарий (вторая строка).
     */
    public String comment;

    //=========================================================================

    /**
     * Конструктор.
     */
    public MenuEntry()
    {
    }

    /**
     * Конструктор.
     * @param code Код.
     * @param comment Комментарий.
     */
    public MenuEntry
        (
            @NotNull String code,
            @Nullable String comment
        )
    {
        this.code = code;
        this.comment = comment;
    }

    //=========================================================================

    @NotNull
    @Override
    @Contract(pure = true)
    public String toString()
    {
        return code + " - " + comment;
    }
}
