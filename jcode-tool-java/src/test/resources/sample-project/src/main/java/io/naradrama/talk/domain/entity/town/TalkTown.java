package io.naradrama.talk.domain.entity.town;

import io.naradrama.talk.domain.entity.room.TalkRoom;
import io.naraplatform.share.domain.NameValue;
import io.naraplatform.share.domain.NameValueList;
import io.naraplatform.share.domain.ddd.DomainAggregate;
import io.naraplatform.share.domain.drama.DramaEntity;
import io.naraplatform.share.domain.patron.CineroomKey;
import io.naraplatform.share.domain.patron.PatronKey;
import io.naraplatform.share.util.json.JsonUtil;
import io.naraplatform.share.util.numeral36.UsidGen;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class TalkTown extends DramaEntity implements DomainAggregate {
    //
    private String name;
    private int sequence;
    private TalkTownSettings talkTownSettings;
    private long time;

    transient private List<TalkRoom> talkRooms;
    transient private List<Participant> participants;

    public TalkTown(String id, PatronKey patronKey) {
        // one per town
        //
        super(id, patronKey);
    }

    public TalkTown(CineroomKey cineroomKey,
                    String name) {
        //
        super(cineroomKey.getKeyString(), cineroomKey);
        this.name = name;
        this.talkTownSettings = new TalkTownSettings();
        this.time = System.currentTimeMillis();
    }

    public String toString() {
        //
        return toJson();
    }

    public static TalkTown fromJson(String json) {
        //
        return JsonUtil.fromJson(json, TalkTown.class);
    }

    public String nextTalkRoomId() {
        //
        return UsidGen.getStr36(getId(), sequence++);
    }

    public void setValues(NameValueList nameValues) {
        //
        for(NameValue nameValue : nameValues.list()) {
            String value = nameValue.getValue();
            switch (nameValue.getName()) {
                case "name":
                    this.name = value;
                    break;
                case "talkTownSettings":
                    this.talkTownSettings = TalkTownSettings.fromJson(value);
                    break;

                default:
                    throw new IllegalArgumentException("Update not allowed: " + nameValue);
            }
        }
    }

    public static TalkTown sample() {
        //
        return new TalkTown(
                CineroomKey.sample(),
                "Talk Plaza"
        );
    }

    public static void main(String[] args) {
        //
        System.out.println(sample());
    }
}