package nextstep.subway.unit;

import nextstep.subway.applicaion.LineService;
import nextstep.subway.applicaion.StationService;
import nextstep.subway.applicaion.dto.LineResponse;
import nextstep.subway.applicaion.dto.SectionRequest;
import nextstep.subway.domain.Line;
import nextstep.subway.domain.LineRepository;
import nextstep.subway.domain.Station;
import nextstep.subway.domain.StationRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class LineServiceMockTest {
    @Mock
    private LineRepository lineRepository;
    @Mock
    private StationRepository stationRepository;

    @Test
    void addSection() {
        // given
        // stationRepository와 lineRepository를 활용하여 초기값 셋팅
        long lineId = 1L;
        long upStationId = 1L;
        long downStationId = 2L;
        int distance = 10;

        SectionRequest sectionRequest = new SectionRequest(upStationId, downStationId, distance);
        LineService lineService = new LineService(lineRepository, stationRepository);
        when(lineRepository.findById(lineId)).thenReturn(Optional.of(new Line("2호선", "green")));
        when(stationRepository.findById(upStationId)).thenReturn(Optional.of(new Station("상행역")));
        when(stationRepository.findById(downStationId)).thenReturn(Optional.of(new Station("히행역")));

        // when
        // lineService.addSection 호출
        LineResponse lineResponse = lineService.addSection(lineId, sectionRequest);

        // then
        // line.getSections 메서 드를 통해 검증
        assertThat(lineResponse.getId()).isNotNull();
    }
}
