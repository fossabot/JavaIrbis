package ru.arsmagna.search;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import ru.arsmagna.Utility;
import ru.arsmagna.infrastructure.*;

import java.util.ArrayList;

/**
 * Информация о поисковом терме.
 */
public final class TermInfo
{
    /**
     * Количество ссылок.
     */
    public int count;

    /**
     * Поисковый термин.
     */
    public String text;

    //=========================================================================

    /**
     * Клонирование.
     * @return Копию.
     */
    @NotNull
    public TermInfo clone()
    {
        TermInfo result = new TermInfo();
        result.count = count;
        result.text = text;

        return result;
    }

    /**
     * Разбор ответа сервера.
     * @param response Ответ сервера.
     * @return Прочитанные термы.
     */
    @NotNull
    public static TermInfo[] parse
        (
            @NotNull ServerResponse response
        )
    {
        ArrayList<TermInfo> result = new ArrayList<>();
        while (true)
        {
            String line = response.readUtf();
            if (Utility.isNullOrEmpty(line))
            {
                break;
            }
            String[] parts = line.split("#", 2);
            TermInfo item = new TermInfo();
            item.count = Integer.parseInt(parts[0]);
            if (parts.length > 1)
            {
                item.text = parts[1];
            }
            result.add(item);
        }

        return result.toArray(new TermInfo[0]);
    }

    //=========================================================================

    @NotNull
    @Override
    @Contract(pure = true)
    public String toString()
    {
        return count + "#" + text;
    }
}
