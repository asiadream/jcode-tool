package io.naradrama.talk.spec.task.town;

import io.naradrama.talk.domain.entity.town.TalkTown;
import io.naradrama.talk.spec.task.town.sdo.TalkTownCdo;
import io.naraplatform.share.domain.DoNotOpen;
import io.naraplatform.share.domain.FacadeService;
import io.naraplatform.share.domain.NameValueList;
import io.naraplatform.share.domain.drama.FeatureTarget;
import io.naraplatform.share.domain.drama.ServiceFeature;

import java.util.List;

@FacadeService
@ServiceFeature(
        name = "TalkTown",
        editions = {"Professional", "Enterprise"},
        authorizedRoles = {"Admin"}
)
public interface TalkTownService extends FeatureTarget {
    //
    @DoNotOpen
    String registerTalkTown(TalkTownCdo talkTownCdo);

    TalkTown findTalkTown(String talkTownId);
    List<TalkTown> findAllTalkTowns();
    void modifyTalkTown(String talkTownId, NameValueList nameValues);
    void removeTalkTown(String talkTownId);
    long countAllTalkTowns();
}