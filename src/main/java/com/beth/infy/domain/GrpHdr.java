package com.beth.infy.domain;

public class GrpHdr extends Pacs {

    private String MsgId;
    private String CreDtTm;
    private String NbOfTxs;

    public String getMsgId() {
        return MsgId;
    }

    public void setMsgId(String msgId) {
        MsgId = msgId;
    }

    public String getCreDtTm() {
        return CreDtTm;
    }

    public void setCreDtTm(String creDtTm) {
        CreDtTm = creDtTm;
    }

    public String getNbOfTxs() {
        return NbOfTxs;
    }

    public void setNbOfTxs(String nbOfTxs) {
        NbOfTxs = nbOfTxs;
    }
}
