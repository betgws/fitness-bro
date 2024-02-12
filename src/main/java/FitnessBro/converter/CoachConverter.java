package FitnessBro.converter;

import FitnessBro.domain.Coach;
import FitnessBro.domain.CoachImage;
import FitnessBro.domain.ReviewImage;
import FitnessBro.service.CoachService.CoachService;
import FitnessBro.web.dto.Coach.CoachResponseDTO;
import java.util.List;
import java.util.stream.Collectors;

public class CoachConverter {

    public static CoachResponseDTO.CoachProfileDTO toCoachProfileDTO(Coach coach){
        return CoachResponseDTO.CoachProfileDTO.builder()
                .nickname(coach.getNickname())
                .age(coach.getAge())
                .rating(coach.getRating())
                .comment(coach.getComment())
                .introduction(coach.getIntroduction())
                .price(coach.getPrice())
                .address(coach.getAddress())
                .schedule(coach.getSchedule())
                .coachPicture(coach.getPictureURL())
                .build();
    }

    public static CoachResponseDTO.CoachDTO toCoachDTO(Coach coach){
        return CoachResponseDTO.CoachDTO.builder()
                .name(coach.getNickname())
                .age(coach.getAge())
                .rating(coach.getRating())
                .comment(coach.getComment())
                .price(coach.getPrice())
                .address(coach.getAddress())
                .build();
    }

    public static List<CoachResponseDTO.CoachDTO> toCoachListDTO(List<Coach> coaches) {
        // Coach 엔티티 리스트를 CoachDTO 리스트로 변환
        return coaches.stream()
                .map(coach -> toCoachDTO(coach)) // toCoachDTO 메서드를 사용하여 Coach를 CoachDTO로 변환
                .collect(Collectors.toList()); // collect를 사용하여 리스트로 반환.
    }

    public static CoachResponseDTO.CoachMyPageDTO toCoachMyPageDTO(Coach coach, Long matchNum, Long reviewNum){
        return CoachResponseDTO.CoachMyPageDTO.builder()
                .nickname(coach.getNickname())
                .matchNum(matchNum)
                .reviewNum(reviewNum)
                .build();
    }
    public static CoachResponseDTO.favoriteCoachDTO toFavoriteCoachDTO(Coach coach){
        // Coach 엔티티를 FavoriteCoachDTO로 변환
        return  CoachResponseDTO.favoriteCoachDTO.builder()
                .coachId(coach.getId())
                .nickname(coach.getNickname())
                .address(coach.getAddress())
                .rating(coach.getRating())
                .build();
    }

    public static CoachResponseDTO.CoachUpdateResponseDTO toCoachUpdateDTO(Coach coach) {
        return CoachResponseDTO.CoachUpdateResponseDTO.builder()
                .nickname(coach.getNickname())
                .email(coach.getEmail())
                .password(coach.getPassword())
                .address(coach.getAddress())
                .comment(coach.getComment())
                .price(coach.getPrice())
                .schedule(coach.getSchedule())
                .introduction(coach.getIntroduction())
                .build();
    }


    public static CoachImage toCoachAlbum(String pictureUrl, Coach coach) {
        return CoachImage.builder()
                .url(pictureUrl)
                .coach(coach)
                .build();
    }

    public static CoachResponseDTO.CoachAlbumDTO toCoachAlbumDTO(Coach coach) {
        return CoachResponseDTO.CoachAlbumDTO.builder()
                .coachId(coach.getId())
                .pictureURLs(coach.getCoachImageList().stream().
                        map(CoachImage::getUrl).
                        collect(Collectors.toList()))
                .build();
    }
}
