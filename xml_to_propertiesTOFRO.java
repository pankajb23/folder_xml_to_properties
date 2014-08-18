
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.lang.StringBuilder;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author pabhardw
 */
public class xml_to_propertiesTOFRO
{
    public static void xml_to_properties(FileReader inputfile,FileWriter outfile)
    {
        BufferedReader br=new BufferedReader(inputfile);
        BufferedWriter bw=new BufferedWriter(outfile);
        String line=null;
        StringBuilder key=new StringBuilder(),value=new StringBuilder(),comment=new StringBuilder();
        boolean flag=false,kv_pair=false;
        try {
            while((line=br.readLine())!=null) {
                line=line.trim();
                if(line.startsWith("<!--")) {
                    flag=true;
                    comment.setLength(0);
                    String[] line_=line.split("<!--");
                    if(line.endsWith("-->")) {
                        //both are happening
                        String[] get=line.split("-->");
                        String[] write=get[0].split("<!--");
                        bw.write("# ");
                        for(int i=0; i<write.length; i++)
                            bw.write(write[i]+" ");
                        bw.write("\n");
                    } else {
                        comment.append(line.split("<!--"));
                    }
                } else if(flag) { //if there is a comment then
                    if(line.endsWith("-->")) {
                        comment.append(line.split("-->"));
                        flag=false;
                        bw.write("# "+comment.toString()+"\n");
                        System.out.println(comment.toString());
                        comment.setLength(0);
                    } else {
                        comment.append(line);
                    }
                }
                if(line.startsWith("<") && !line.startsWith("<!--") ) {
                    String[] args=line.split("</string>");
                    String[] pair=args[0].split(">");
                    String[] get=pair[0].split("=");
                    String key_=get[1].substring(1,get[1].length()-1);
                    bw.write(key_+"="+pair[1]+"\n");
                    //System.out.println(key_+"="+pair[1]+"\n");
                }
            }
            bw.flush();
            br.close();
            bw.close();
            inputfile.close();
            outfile.close();

        } catch(Exception ex) {
            ex.printStackTrace();
        }
    }
    public static void properties_to_xml(FileReader inputfile,FileWriter outfile)
    {
        BufferedReader br=new BufferedReader(inputfile);
        BufferedWriter bw=new BufferedWriter(outfile);
        String line=null;
        try {
            bw.write("<resource>\n");
            while((line=br.readLine())!=null) {
                line.trim();
                if(line.charAt(0)=='#') { //sure shot a comment
                    int len=line.length();
                    String temp=line.substring(1);
                    bw.write("<!--"+temp.trim()+" -->\n");
                } else {
                    String[] args=line.split("=");
                    bw.write("<string name="+"\""+args[0]+"\"> "+args[1]+"</string>\n");
                }
            }
            bw.write("</resource>\n");
            bw.close();
            br.close();
            inputfile.close();
            outfile.close();
        } catch(Exception ex) {
            ex.printStackTrace();
        }
    }
    public static void main(String[] args)
    {
        try {
            FileReader inputfile=new FileReader("strings.properties");
            FileWriter outfile=new FileWriter("strings1.xml");
            boolean flag=false;
            /***/
            //flag set accodingly
            if(flag)
                xml_to_properties(inputfile,outfile);
            else
                properties_to_xml(inputfile,outfile);
        } catch(Exception ex) {
            System.out.println(ex+"");
        }
    }
}
