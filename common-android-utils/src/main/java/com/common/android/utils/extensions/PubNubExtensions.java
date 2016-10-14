package com.common.android.utils.extensions;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.pubnub.api.models.consumer.PNErrorData;
import com.pubnub.api.models.consumer.PNStatus;
import com.pubnub.api.models.consumer.presence.PNHereNowChannelData;
import com.pubnub.api.models.consumer.presence.PNHereNowOccupantData;
import com.pubnub.api.models.consumer.pubsub.PNMessageResult;
import com.pubnub.api.models.consumer.pubsub.PNPresenceEventResult;

import java.util.List;

import static com.common.android.utils.extensions.CollectionExtensions.isEmpty;

/**
 * Created by <a href="https://about.me/janrabe">Jan Rabe</a>.
 */

public class PubNubExtensions {

    private PubNubExtensions() throws IllegalAccessException {
        throw new IllegalAccessException();
    }

    @NonNull
    public static String toStringPNStatus(@Nullable final PNStatus result) {
        return result == null
                ? ""
                : "PNStatus{" +
                "category=" + result.getCategory() +
                ", errorData=" + toStringPNErrorData(result.getErrorData()) +
                ", error=" + result.isError() +
                ", statusCode=" + result.getStatusCode() +
                ", operation=" + result.getOperation() +
                ", tlsEnabled=" + result.isTlsEnabled() +
                ", uuid='" + result.getUuid() + '\'' +
                ", authKey='" + result.getAuthKey() + '\'' +
                ", origin='" + result.getOrigin() + '\'' +
                ", clientRequest=" + result.getClientRequest() +
                ", affectedChannels=" + result.getAffectedChannels() +
                ", affectedChannelGroups=" + result.getAffectedChannelGroups() +
                '}';

    }

    @NonNull
    public static String toStringPNMessageResult(@Nullable final PNMessageResult result) {
        return result == null
                ? ""
                : "PNMessageResult{" +
                "message=" + result.getMessage() +
                ", subscribedChannel='" + result.getSubscribedChannel() + '\'' +
                ", actualChannel='" + result.getActualChannel() + '\'' +
                ", timetoken=" + result.getTimetoken() +
                ", userMetadata=" + result.getUserMetadata() +
                '}';

    }

    @NonNull
    public static String toStringPNPresenceEventResult(@Nullable final PNPresenceEventResult result) {
        return result == null
                ? ""
                : "PNPresenceEventResult{" +
                "event='" + result.getEvent() + '\'' +
                ", uuid='" + result.getUuid() + '\'' +
                ", timestamp=" + result.getTimestamp() +
                ", occupancy=" + result.getOccupancy() +
                ", state=" + result.getState() +
                ", subscribedChannel='" + result.getSubscribedChannel() + '\'' +
                ", actualChannel='" + result.getActualChannel() + '\'' +
                ", timetoken=" + result.getTimetoken() +
                ", userMetadata=" + result.getUserMetadata() +
                '}';
    }

    @NonNull
    public static String toStringPNErrorData(@Nullable final PNErrorData result) {
        return result == null
                ? ""
                : "PNErrorData{" +
                "information='" + result.getInformation() + '\'' +
                ", throwable=" + result.getThrowable() +
                '}';
    }

    @NonNull
    public static String toStringPNHereNowOccupantData(@Nullable final PNHereNowOccupantData result) {
        return result == null
                ? ""
                : "PNHereNowOccupantData{" +
                "uuid='" + result.getUuid() + '\'' +
                ", state=" + result.getState() +
                '}';
    }

    @NonNull
    public static String toStringPNHereNowOccupants(@Nullable final List<PNHereNowOccupantData> result) {
        if (isEmpty(result))
            return "PNHereNowOccupantData={occupants=null}";

        StringBuilder buffer = new StringBuilder();

        buffer.append("{\n");
        for (PNHereNowOccupantData occupantData : result)
            buffer.append(toStringPNHereNowOccupantData(occupantData)).append("\n");
        buffer.append("}");

        return buffer.toString();
    }

    @NonNull
    public static String toStringPNHereNowChannelData(@Nullable final PNHereNowChannelData result) {
        return result == null
                ? ""
                : "PNHereNowChannelData{" +
                "channelName='" + result.getChannelName() + '\'' +
                ", occupancy=" + result.getOccupancy() +
                ", occupants=" + toStringPNHereNowOccupants(result.getOccupants()) +
                '}';
    }
}
