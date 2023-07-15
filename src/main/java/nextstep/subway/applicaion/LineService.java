package nextstep.subway.applicaion;

import nextstep.subway.applicaion.dto.*;
import nextstep.subway.domain.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
public class LineService {
    private LineRepository lineRepository;

    private StationRepository stationRepository;

    public LineService(LineRepository lineRepository, StationRepository stationRepository) {
        this.lineRepository = lineRepository;
        this.stationRepository = stationRepository;
    }

    @Transactional
    public LineResponse saveLine(LineRequest request) {
        Line line = lineRepository.save(new Line(request.getName(), request.getColor()));
        if (request.getUpStationId() != null && request.getDownStationId() != null && request.getDistance() != 0) {
            Station upStation = stationRepository.findById(request.getUpStationId()).orElseThrow(RuntimeException::new);
            Station downStation = stationRepository.findById(request.getDownStationId()).orElseThrow(RuntimeException::new);
            line.getSections().add(new Section(line, upStation, downStation, request.getDistance()));
        }
        return createLineResponse(line);
    }

    public List<LineResponse> showLines() {
        return lineRepository.findAll().stream()
                .map(this::createLineResponse)
                .collect(Collectors.toList());
    }

    public LineResponse findById(Long id) {
        return createLineResponse(lineRepository.findById(id).orElseThrow(IllegalArgumentException::new));
    }

    @Transactional
    public void updateLine(Long id, LineRequest lineRequest) {
        Line line = lineRepository.findById(id).orElseThrow(IllegalArgumentException::new);

        if (lineRequest.getName() != null) {
            line.setName(lineRequest.getName());
        }
        if (lineRequest.getColor() != null) {
            line.setColor(lineRequest.getColor());
        }
    }

    @Transactional
    public void deleteLine(Long id) {
        lineRepository.deleteById(id);
    }

    @Transactional
    public LineResponse addSection(Long lineId, SectionRequest sectionRequest) {

        Line line = lineRepository.findById(lineId).orElseThrow(RuntimeException::new);
        Station upStation = stationRepository.findById(sectionRequest.getUpStationId()).orElseThrow(RuntimeException::new);
        Station downStation = stationRepository.findById(sectionRequest.getDownStationId()).orElseThrow(RuntimeException::new);

        line.addSection(upStation, downStation, sectionRequest.getDistance());

        return new LineResponse(line.getId(), line.getName(), line.getColor(), createSectionResponse(line.getSections()));
    }

    private List<SectionResponse> createSectionResponse(List<Section> sections) {
        return null;
    }

    private LineResponse createLineResponse(Line line) {
        return new LineResponse(
                line.getId(),
                line.getName(),
                line.getColor()
        );
    }

//    private List<StationResponse> createStationResponses(Line line) {
//        if (line.getSections().isEmpty()) {
//            return Collections.emptyList();
//        }
//
//        List<Station> stations = line.getSections().stream()
//                .map(Section::getDownStation)
//                .collect(Collectors.toList());
//
//        stations.add(0, line.getSections().get(0).getUpStation());
//
//        return stations.stream()
//                .map(it -> stationRepository.createStationResponse(it))
//                .collect(Collectors.toList());
//    }

    @Transactional
    public void deleteSection(Long lineId, Long stationId) {
        Line line = lineRepository.findById(lineId).orElseThrow(IllegalArgumentException::new);
        Station station = stationRepository.findById(stationId).orElseThrow(RuntimeException::new);

        if (!line.getSections().get(line.getSections().size() - 1).getDownStation().equals(station)) {
            throw new IllegalArgumentException();
        }

        line.getSections().remove(line.getSections().size() - 1);
    }
}
