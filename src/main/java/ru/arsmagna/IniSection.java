package ru.arsmagna;

import org.jetbrains.annotations.*;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Секция INI-файла.
 */
public final class IniSection
{
    /**
     * Имя секции.
     */
    public String name;

    /**
     * Строки.
     */
    public Collection<IniLine> lines;

    //=========================================================================

    /**
     * Конструктор по умолчанию.
     */
    public IniSection()
    {
        lines = new ArrayList<>();
    }

    /**
     * Конструктор.
     * @param name Имя секции.
     */
    public IniSection
        (
            String name
        )
    {
        this.name = name;
        lines = new ArrayList<>();
    }

    //=========================================================================

    @NotNull
    @Override
    @Contract(pure = true)
    public String toString()
    {
        return "[" + name + "]";
    }
}
