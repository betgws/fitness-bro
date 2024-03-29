package FitnessBro.service.ChatService;

import FitnessBro.domain.Chat.ChatMessage;
import FitnessBro.domain.Chat.ChatRoom;
import FitnessBro.domain.Coach;
import FitnessBro.domain.Member;

import java.util.List;

public interface ChatRoomService {

   // List<ChatRoom> findAllRoom();
    ChatRoom findById(Long roomId);
    ChatRoom createRoom(Long roomId, Long memberId, Long coachId);

    List<ChatRoom> findAllChatRoomListByMemberId(Long memberId);
    List<ChatRoom> findAllChatRoomListByCoachId(Long coachId);
    void setLastChatMessage(List<ChatRoom> chatRoomList);

    ChatRoom findChatRoomByMemberIdAndCoachId(Long memberId, Long coachId);
}
