/**
 * Created by dell on 10/7/2016.
 */
import  jxl.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

import jxl.write.*;
import jxl.write.Number;
import org.json.simple.JSONObject;


public class WriteExcel {
    public void Write(ArrayList<CustomInfo> customInfos){
        try{
            String fileName= "E:\\ResearchScience_NKC\\Data\\data.xls";
            WritableWorkbook workbook= Workbook.createWorkbook(new File(fileName));
            WritableSheet sheet= workbook.createSheet("Info3",0);
            Label position= new Label(0,0,"Position");
            sheet.addCell(position);
            Label id= new Label(1,0,"Id");
            sheet.addCell(id);
            sheet.addCell(new Label(2,0,"ThreadId"));
            Label labelIds= new Label(3,0,"LabelIds");
            sheet.addCell(labelIds);
            Label received= new Label(4,0,"Received");
            sheet.addCell(received);
            Label date= new Label(5,0,"Date");
            sheet.addCell(date);
            Label subject= new Label(6,0,"Subject");
            sheet.addCell(subject);
            Label codeProfile= new Label(7,0,"CodeProfile");
            sheet.addCell(codeProfile);
            Label codeTicet= new Label(8,0,"CodeTicket");
            sheet.addCell(codeTicet);
            int row=0;

            for(CustomInfo ci: customInfos){
                row+=1;
                sheet.addCell(new Number(0,row, row));

                sheet.addCell(new Label(1,row,ci.GetId()));
                sheet.addCell(new Label(2,row,ci.GetThreadId()));
                sheet.addCell(new Label(3,row,ci.GetLabelIds().toString()));
                sheet.addCell(new Label(4,row,ci.GetReceived()));
                sheet.addCell(new Label(5,row,ci.GetDate()));
                sheet.addCell(new Label(6,row,ci.GetSubject()));
                sheet.addCell(new Label(7,row,ci.CodeProfile().toString()));
                sheet.addCell(new Label(8,row,ci.CodeTicket().toString()));
            }
            workbook.write();
            workbook.close();
        }
        catch (Exception e){
            e.getStackTrace();
        }
    }
}
