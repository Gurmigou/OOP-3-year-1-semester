package Lab_1.disk;

import Lab_1.music.Music;
import Lab_1.music.util.MusicType;
import lombok.Getter;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Getter
public class RecordDisk {
    private final List<Music> records;

    public RecordDisk(Music... records) {
        this.records = new ArrayList<>(List.of(records));
    }

    public void addMusic(Music record) {
        records.add(record);
    }

    public void removeMusic(Music record) {
        records.remove(record);
    }

    public int diskSize() {
        return records.size();
    }

    public List<Music> findDurationBetween(int min, int max) {
        List<Music> result = new ArrayList<>();
        for (Music record : records) {
            if (record.getDuration() >= min && record.getDuration() <= max) {
                result.add(record);
            }
        }
        return result;
    }

    public void sortByMusicType(Comparator<MusicType> comparator) {
        records.sort((o1, o2) -> comparator.compare(o1.getType(), o2.getType()));
    }

    public String getMusicRecordsNames() {
        return this.records
                .stream()
                .map(Music::getName)
                .collect(Collectors.joining(", "));
    }
}
