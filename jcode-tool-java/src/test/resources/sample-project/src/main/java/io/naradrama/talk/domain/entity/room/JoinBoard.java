/*
 * COPYRIGHT (c) NEXTREE Consulting 2014
 * This software is the proprietary of NEXTREE Consulting CO.
 * @since 2014. 6. 10.
 */

package io.naradrama.talk.domain.entity.room;

import io.naradrama.talk.domain.entity.town.Participant;
import io.naraplatform.share.domain.IdName;
import io.naraplatform.share.domain.NameValue;
import io.naraplatform.share.domain.NameValueList;
import io.naraplatform.share.domain.ddd.DomainAggregate;
import io.naraplatform.share.domain.drama.DramaEntity;
import io.naraplatform.share.domain.patron.CineroomKey;
import io.naraplatform.share.domain.patron.PatronKey;
import io.naraplatform.share.util.json.JsonUtil;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class JoinBoard extends DramaEntity implements DomainAggregate {
    //
    private IdName talkRoom;
    private IdName participant;
    private String photoDepotId;
    private LastRead lastRead;

    private LastTalk lastTalk;          // OneToOne, don't make persistence.

    public JoinBoard(String id,
                     PatronKey patronKey) {
        //
        super(id, patronKey);
    }

    public JoinBoard(String roomJoinBoardId,
                     CineroomKey cineroomKey,
                     IdName talkRoom,
                     IdName participant) {
        //
        super(roomJoinBoardId, cineroomKey);
        this.talkRoom = talkRoom;
        this.participant = participant;
        this.lastTalk = null;
        this.lastRead = new LastRead(1);
    }

    public String toString() {
        //
        return toJson();
    }

    public static JoinBoard fromJson(String json) {
        //
        return JsonUtil.fromJson(json, JoinBoard.class);
    }

    public void setValues(NameValueList nameValues) {
        //
        for(NameValue nameValue : nameValues.list()) {
            String value = nameValue.getValue();
            switch (nameValue.getName()) {
                case "lastTalk":
                    this.lastTalk = LastTalk.fromJson(value);
                    break;
                case "photoDepotId":
                    this.photoDepotId = value;
                    break;
                case "lastRead":
                    this.lastRead = LastRead.fromJson(value);
                    break;

                default:
                    throw new IllegalArgumentException("Update not allowed: " + nameValue);
            }
        }
    }

    public static JoinBoard sample() {
        //
        TalkRoom talkRoom = TalkRoom.sample();
        Participant participant = Participant.sample();

        return new JoinBoard(
                talkRoom.nextRoomJoinBoardId(),
                CineroomKey.sample(),
                talkRoom.getIdName(),
                participant.getIdName()
        );
    }

    public static void main(String[] args) {
        //
        System.out.println(sample());
    }
}