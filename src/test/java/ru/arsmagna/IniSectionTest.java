package ru.arsmagna;

import org.junit.*;

import static org.junit.Assert.*;

public class IniSectionTest
{
    @Test
    public void toString_1()
    {
        IniSection section = new IniSection();
        assertEquals("[null]", section.toString());

        section = new IniSection("Hello");
        assertEquals("[Hello]", section.toString());

        section.lines.add(new IniLine("Key1", "Value1"));
        assertEquals("[Hello]", section.toString());
    }
}