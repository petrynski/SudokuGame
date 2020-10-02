import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.util.Scanner;

class RankingManager {
    RankingEntry[] ranking;

    RankingManager() throws FileNotFoundException {
        ranking = new RankingEntry[10];
        load();
    }

    void addEntry(RankingEntry newEntry)
    {
        RankingEntry tempEntry = new RankingEntry(newEntry.time,newEntry.name);
        for (RankingEntry i: ranking) {
            if(newEntry.time < i.time)
            {
                RankingEntry helpHand = new RankingEntry(i.time,i.name);
                i.time = tempEntry.time;
                i.name = tempEntry.name;
                tempEntry.name = helpHand.name;
                tempEntry.time = helpHand.time;
            }

        }
    }

    void load() throws FileNotFoundException {
        File file = new File("ranking.txt");
        Scanner sc = new Scanner(file);
        int i = 0;
        while (sc.hasNextLine()) {
            ranking[i] = new RankingEntry(sc.nextLong(),sc.next());
            i++;
            if(i==10)
                return;
        }
        while(i<=9)
        {
            ranking[i] = new RankingEntry(99999,"ABC");
            i++;
        }
    }

    void save()
    {
        try{
            File file = new File("ranking.txt");
            BufferedWriter bw = new BufferedWriter(new FileWriter(file));
            for (RankingEntry i: ranking) {
                System.out.println(i.time + " " + i.name + "\n");
                bw.write(i.time + " " + i.name + "\n");
            }
            bw.close();
        }catch(Exception ex){
            ex.printStackTrace();
        }
    }
}
