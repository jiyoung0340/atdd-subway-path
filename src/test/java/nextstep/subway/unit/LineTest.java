package nextstep.subway.unit;

import nextstep.subway.domain.Line;
import nextstep.subway.domain.Section;
import nextstep.subway.domain.Station;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class LineTest {
    @Test
    void addSection() {
        Section section = 구간_추가하기("강남역", "역삼역", 10);

        Line line = new Line("2호선", "green");
        line.addSection(section.getUpStation(), section.getDownStation(), section.getDistance());
        assertThat(line.getSections().size()).isEqualTo(1);

    }

    @Test
    void getStations() {
        Section section = 구간_추가하기("사당역", "신림역", 7);

        Line line = new Line("2호선", "green");
        line.addSection(section.getUpStation(), section.getDownStation(), section.getDistance());

        assertThat(line.getSections().get(0).getUpStation().getName()).isEqualTo(section.getUpStation().getName());
    }

    @Test
    void removeSection() {
        Section section1 = 구간_추가하기("사당역", "신림역", 10);
        Section section2 = 구간_추가하기("강남역", "역삼역", 7);
        Section section3 = 구간_추가하기("삼성역", "잠실역", 4);

        Line line = new Line("2호선", "green");
        line.addSection(section1.getUpStation(), section1.getDownStation(), section1.getDistance());
        line.addSection(section2.getUpStation(), section2.getDownStation(), section2.getDistance());
        line.addSection(section3.getUpStation(), section3.getDownStation(), section3.getDistance());

        line.removeSection(section1.getId());

        assertThat(line.getSections().size()).isEqualTo(2);
    }

    public Section 구간_추가하기(String upStationName, String downStationName, int distance) {
        Station up = new Station(upStationName);
        Station down = new Station(downStationName);

        return new Section(up, down, distance);
    }
}
