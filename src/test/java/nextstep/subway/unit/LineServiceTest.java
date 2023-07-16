package nextstep.subway.unit;

import nextstep.subway.applicaion.LineService;
import nextstep.subway.applicaion.dto.LineResponse;
import nextstep.subway.applicaion.dto.SectionRequest;
import nextstep.subway.domain.Line;
import nextstep.subway.domain.LineRepository;
import nextstep.subway.domain.Station;
import nextstep.subway.domain.StationRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class LineServiceTest {

    @Autowired
    private StationRepository stationRepository;

    @Autowired
    private LineRepository lineRepository;

    @Autowired
    private LineService lineService;

    @Test
    @DirtiesContext
    void addSection() {
        // given
        Station 왕십리 = stationRepository.save(new Station("왕십리"));
        Station 잠실 = stationRepository.save(new Station("잠실"));
        Line 이호선 = lineRepository.save(new Line("2호선", "green"));
        int distance = 10;
        // when
        SectionRequest sectionRequest = new SectionRequest(왕십리.getId(), 잠실.getId(), distance);
        LineResponse lineResponse = lineService.addSection(이호선.getId(), sectionRequest);

        // then
        assertThat(이호선.getId()).isEqualTo(lineResponse.getId());
    }


}
