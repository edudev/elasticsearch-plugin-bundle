package org.xbib.elasticsearch.action.langdetect;

import org.elasticsearch.action.ActionResponse;
import org.elasticsearch.common.Strings;
import org.elasticsearch.common.xcontent.StatusToXContentObject;
import org.elasticsearch.common.xcontent.ToXContent;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.rest.RestStatus;
import org.xbib.elasticsearch.common.langdetect.Language;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.elasticsearch.rest.RestStatus.OK;

/**
 *
 */
public class LangdetectResponse extends ActionResponse implements StatusToXContentObject {

    private String profile;

    private List<Language> languages = new ArrayList<>();

    public String getProfile() {
        return profile;
    }

    public LangdetectResponse setProfile(String profile) {
        this.profile = profile;
        return this;
    }

    public List<Language> getLanguages() {
        return languages;
    }

    public LangdetectResponse setLanguages(List<Language> languages) {
        this.languages = languages;
        return this;
    }

    @Override
    public XContentBuilder toXContent(XContentBuilder builder, ToXContent.Params params) throws IOException {
        builder.startObject();
        if (!Strings.isNullOrEmpty(profile)) {
            builder.field("profile", profile);
        }
        builder.startArray("languages");
        for (Language lang : languages) {
            builder.startObject().field("language", lang.getLanguage())
                    .field("probability", lang.getProbability()).endObject();
        }
        builder.endArray();
        builder.endObject();
        return builder;
    }

    @Override
    public RestStatus status() {
        return OK;
    }
}
