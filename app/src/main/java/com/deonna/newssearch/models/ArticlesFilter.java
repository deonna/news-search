package com.deonna.newssearch.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class ArticlesFilter implements Parcelable {

    private static final String FORMAT_PATTERN = "yyyyMMdd";
    public static final int SORT_NEWEST = 0;
    public static final int SORT_OLDEST = 1;

    public String beginDateFormatted;
    public Date beginDate;

    public int sortOrder;
    public String sortOrderParam;

    public Map<String, Boolean> topics;

    public ArticlesFilter() {

        beginDate = new Date();
        sortOrder = SORT_NEWEST;
        topics = new HashMap<>();
    }

    protected ArticlesFilter(Parcel in) {

        beginDate = (Date) in.readSerializable();
        beginDateFormatted = in.readString();
        sortOrder = in.readInt();
        sortOrderParam = in.readString();

        int size = in.readInt();

        for(int i = 0; i < size; i++){
            String key = in.readString();
            Boolean value = (in.readInt() != 0);
            topics.put(key,value);
        }

    }

    public static final Creator<ArticlesFilter> CREATOR = new Creator<ArticlesFilter>() {
        @Override
        public ArticlesFilter createFromParcel(Parcel in) {
            return new ArticlesFilter(in);
        }

        @Override
        public ArticlesFilter[] newArray(int size) {
            return new ArticlesFilter[size];
        }
    };

    public String setFormattedDate(int year, int month, int day) {

        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, day);

        beginDate = new Date(calendar.getTimeInMillis());
        beginDateFormatted = formatDate(beginDate);

        return beginDateFormatted;
    }

    public static String formatDate(Date date) {

        SimpleDateFormat formatter = new SimpleDateFormat(FORMAT_PATTERN, Locale.US);

        String formattedDate = formatter.format(date);

        return formattedDate;
    }

    public void setSortOrder(int sortOrder, String sortOrderParam) {

        this.sortOrder = sortOrder;
        this.sortOrderParam = sortOrderParam;
    }

    public void addTopic(String topic, Boolean isChecked) {

        topics.put(topic.toLowerCase(), isChecked);
    }

    public String makeNewsDeskQuery() {

        String newsDeskFilter = null; //Format: fq=news_desk:("Education"%20"Health")

        for(Map.Entry<String, Boolean> entry : topics.entrySet()) {

            String topic = entry.getKey();
            Boolean isSelected = entry.getValue();

            if (isSelected) {

                final String NEWS_DESK_PREFIX_FORMAT = "news_desk:(\"%s\"";
                final String NEWS_DESK_TOPIC_FORMAT = "%s%%20\"%s\"";

                if (newsDeskFilter == null) {
                    newsDeskFilter = String.format(NEWS_DESK_PREFIX_FORMAT, topic);
                } else {
                    newsDeskFilter = String.format(NEWS_DESK_TOPIC_FORMAT, newsDeskFilter, topic);
                }
            }
        }

        if (newsDeskFilter != null) {
            final String NEWS_DESK_SUFFIX_FORMAT = "%s)";
            newsDeskFilter = String.format(NEWS_DESK_SUFFIX_FORMAT, newsDeskFilter);
        }

        return newsDeskFilter;
    }

    public boolean isTopicSelected(String topic) {

        topic = topic.toLowerCase();

        if (!topics.containsKey(topic)) {
            return false;
        }

        return topics.get(topic);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

        dest.writeSerializable(beginDate);
        dest.writeString(beginDateFormatted);
        dest.writeInt(sortOrder);
        dest.writeString(sortOrderParam);

        dest.writeInt(topics.size());

        for(Map.Entry<String, Boolean> entry : topics.entrySet()){
            dest.writeString(entry.getKey());
            dest.writeInt(entry.getValue() ? 1 : 0);     //if myBoolean == true, byte == 1
        }
    }
}
