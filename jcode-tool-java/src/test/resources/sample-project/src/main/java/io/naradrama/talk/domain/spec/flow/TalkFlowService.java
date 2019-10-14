/*
 * COPYRIGHT (c) NEXTREE Consulting 2014
 * This software is the proprietary of NEXTREE Consulting CO.
 * @since 2014. 6. 10.
 */

package io.naradrama.talk.domain.spec.flow;

import io.naradrama.talk.domain.entity.talk.Talk;
import io.naradrama.talk.domain.sdo.ListenInfo;
import io.naradrama.talk.domain.sdo.TalkCdo;
import io.naraplatform.share.domain.FacadeCandidate;

import java.util.List;

@FacadeCandidate
public interface TalkFlowService {
    //
    String talk(TalkCdo talkCdo);
    List<Talk> listen(ListenInfo listenInfo);
}