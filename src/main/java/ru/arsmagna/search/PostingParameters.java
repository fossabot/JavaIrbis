package ru.arsmagna.search;

import org.jetbrains.annotations.*;

/**
 * Parameters for ReadPostingsCommand.
 */
public final class PostingParameters
{
    /**
     * База данных.
     */
    public String database;

    /**
     * Номер первого поистинга.
     */
    public int firstPosting;

    /**
     * Формат.
     */
    public String format;

    /**
     * Требуемое количество постингов.
     */
    public int numberOfPostings;

    /**
     * Терм.
     */
    public String term;

    /**
     * Список термов.
     */
    public String[] listOftTerms;

    //=========================================================================

    /**
     * Конструктор.
     */
    public PostingParameters()
    {
        firstPosting = 1;
    }

    //=========================================================================

    /**
     * Клонирование.
     * @return Копию.
     */
    @NotNull
    public PostingParameters clone()
    {
        PostingParameters result = new PostingParameters();
        result.database = database;
        result.firstPosting = firstPosting;
        result.numberOfPostings = numberOfPostings;
        result.term = term;
        result.listOftTerms = listOftTerms;

        return result;
    }

    //=========================================================================
}
