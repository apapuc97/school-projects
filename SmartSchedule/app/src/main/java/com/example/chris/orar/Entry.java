package com.example.chris.orar;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;

/**
 * Created by Alexandru Papuc on 21.12.2017.
 */

public class Entry {
    public  int             id;
    public  String          studentName;
    public  String          media;
    public  String          lucru;
    private final String    ns = null;

    private Entry(int id, String studentName, String media, String lucru) {
        this.id = id;
        this.studentName = studentName;
        this.media = media;
        this.lucru = lucru;
    }

    // Parses the contents of an entry. If it encounters a title, summary, or link tag, hands them off
// to their respective "read" methods for processing. Otherwise, skips the tag.
    public Entry    readEntry(XmlPullParser parser) throws XmlPullParserException, IOException {
        parser.require(XmlPullParser.START_TAG, ns, "DateStudent");
        int id = -1;
        String studentName = null;
        String media = null;
        String lucru = null;
        while (parser.next() != XmlPullParser.END_TAG) {
            if (parser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }
            String name = parser.getName();
            if (name.equals("id")) {
                id = Integer.parseInt(readField(parser, name));
                if (name.equals("studentName")) {
                    studentName = readField(parser, name);
                } else if (name.equals("media")) {
                    media = readField(parser, name);
                } else if (name.equals("lucru")) {
                    lucru = readField(parser, name);
                } else {
                    skip(parser);
                }
            }
        }
        return new Entry(id, studentName, media, lucru);
    }

    // Processes title tags in the feed.
    private String  readField(XmlPullParser parser, String fieldName) throws IOException, XmlPullParserException  {
        String  field;

        parser.require(XmlPullParser.START_TAG, ns, fieldName);
        field = readText(parser);
        parser.require(XmlPullParser.END_TAG, ns, fieldName);
        return field;

    }

    // For the tags title and summary, extracts their text values.
    private String readText(XmlPullParser parser) throws IOException, XmlPullParserException {
        String result = "";
        if (parser.next() == XmlPullParser.TEXT) {
            result = parser.getText();
            parser.nextTag();
        }
        return result;
    }

    private void skip(XmlPullParser parser) throws XmlPullParserException, IOException {
        if (parser.getEventType() != XmlPullParser.START_TAG) {
            throw new IllegalStateException();
        }
        int depth = 1;
        while (depth != 0) {
            switch (parser.next()) {
                case XmlPullParser.END_TAG:
                    depth--;
                    break;
                case XmlPullParser.START_TAG:
                    depth++;
                    break;
            }
        }
    }
}
