package ru.arsmagna;

import org.junit.*;

import ru.arsmagna.menus.*;
import ru.arsmagna.search.*;

import java.io.IOException;
import java.util.Arrays;

import static org.junit.Assert.*;

public class SimplestClientTest
{
    @Ignore
    @Test
    public void testConnection() throws IOException, IrbisException
    {
        IrbisConnection connection = new IrbisConnection();
        System.out.printf("Connected=%d", connection.isConnected() ? 1 : 0);
        connection.username = "user";
        connection.password = "password";
        connection.workstation = 'A';
        connection.connect();
        System.out.printf("Connected=%d", connection.isConnected() ? 1 : 0);

        IrbisVersion version = connection.getServerVersion();
        System.out.println(version);

        IrbisProcessInfo[] processes = connection.listProcesses();
        System.out.println(Arrays.toString(processes));

        UserInfo[] users = connection.getUserList();
        System.out.println(Arrays.toString(users));

        ServerStat stat = connection.getServerStat();
        System.out.println(stat);

        int maxMfn = connection.getMaxMfn("IBIS");
        System.out.printf("Max MFN (IBIS)=%d", maxMfn);
        System.out.println();

        connection.noOp();

        maxMfn = connection.getMaxMfn("RDR");
        System.out.printf("Max MFN (RDR)=%d", maxMfn);
        System.out.println();

        int[] found = connection.search("K=БЕТОН");
        System.out.printf("Found: %d", found.length);
        System.out.println();

        FileSpecification specification = new FileSpecification(3, "IBIS", "NASPI.txt");
        String text = connection.readTextFile(specification);
        System.out.println(text);

        MarcRecord record = connection.readRecord(connection.database, 1);
        System.out.println(record.toString());

        specification = new FileSpecification(3, "IBIS", "*.txt");
        String[] files = connection.listFiles(specification);
        System.out.println(String.join(", ", files));

        String formatted = connection.formatRecord("@brief", 1);
        System.out.println(formatted);

        record = new MarcRecord();
        record.fields.add(new RecordField(100, "Field100"));
        record.fields.add(new RecordField(200, "Field200"));
        String format = "v100/v200";
        formatted = connection.formatRecord(format, record);
        System.out.println(formatted);

        TermParameters parameters = new TermParameters();
        parameters.database = "IBIS";
        parameters.startTerm = "K=";
        parameters.numberOfTerms = 10;
        TermInfo[] terms = connection.readTerms(parameters);
        for (TermInfo term: terms)
        {
            System.out.println(term);
        }

        PostingParameters postingParameters = new PostingParameters();
        postingParameters.database = "IBIS";
        postingParameters.term = "K=&C";
        postingParameters.firstPosting = 1;
        postingParameters.numberOfPostings = 100;
        TermPosting[] postings = connection.readPostings(postingParameters);
        for (TermPosting posting: postings)
        {
            System.out.println(posting);
        }

        specification = new FileSpecification(IrbisPath.MASTER_FILE, "IBIS", "NAZN.MNU");
        MenuFile menu = MenuFile.read(connection, specification);
        System.out.println(menu);

        record = new MarcRecord();
        record.fields.add(new RecordField(300, "Какие-то примечания"));
        maxMfn = connection.writeRecord(record, false, true, false);
        System.out.printf("New max MFN=%d", maxMfn);
        System.out.println();
        System.out.println(record);

        MarcRecord[] records = new MarcRecord[10];
        for (int i = 0; i < records.length; i++)
        {
            records[i] = new MarcRecord();
            records[i].fields.add(new RecordField(300, "Комментарий " + (i + 1)));
        }
        int returnCode = connection.writeRecords(records, false, true, false);
        System.out.printf("Return Code=%d", returnCode);
        System.out.println();
        for (int i = 0; i < records.length; i++)
        {
            System.out.println(records[i]);
        }

        specification = new FileSpecification(IrbisPath.MASTER_FILE, "IBIS", "no_such_file.txt");
        specification.content = "No such file";
        connection.writeTextFile(specification);

        String noSuchBase = "NOSUCH";
        connection.createDatabase(noSuchBase, "Description", true, null);
        System.out.println("Database created");

        records = new MarcRecord[10];
        for (int i = 0; i < records.length; i++)
        {
            records[i] = new MarcRecord();
            records[i].database = noSuchBase;
            records[i].fields.add(new RecordField(300, "Комментарий " + (i + 1)));
        }
        connection.writeRecords(records, false, false, true);
        System.out.println("Records saved");

        connection.createDictionary(noSuchBase);
        System.out.println("Dictionary created");

        connection.reloadMasterFile(noSuchBase);
        System.out.println("Master file reloaded");

        connection.reloadDictionary(noSuchBase);
        System.out.printf("Dictionary reloaded");

        connection.truncateDatabase(noSuchBase);
        System.out.println("Database truncated");

        connection.unlockDatabase(noSuchBase);
        System.out.println("Database unlocked");

        connection.deleteDatabase(noSuchBase);
        System.out.println("Database deleted");

        connection.actualizeRecord("IBIS", 1);
        System.out.println("Record actualized");

        int[] mfns = new int[]{1, 2, 3};
        connection.unlockRecords("IBIS", mfns);
        System.out.println("Records unlocked");

        connection.restartServer();
        System.out.println("Server restarted");

        connection.disconnect();
    }
}
