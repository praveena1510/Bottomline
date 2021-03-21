package dto;

public class SendEmailResponseDTO
{
    private String threadId;

    private String[] labelIds;

    private String id;

    public String getThreadId ()
    {
        return threadId;
    }

    public void setThreadId (String threadId)
    {
        this.threadId = threadId;
    }

    public String[] getLabelIds ()
    {
        return labelIds;
    }

    public void setLabelIds (String[] labelIds)
    {
        this.labelIds = labelIds;
    }

    public String getId ()
    {
        return id;
    }

    public void setId (String id)
    {
        this.id = id;
    }

}