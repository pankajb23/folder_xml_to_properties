#properties file
fo=open("strings.xml","r")
fw=open("strings.properties","w")

for line in fo:
#   line=line.rstrip()
   line=line.lstrip();
   if len(line) ==0:
       #do nothing line is empty
       a=10
   elif line.startswith("<!--"):
       #do nothing only a commnet
       lst=line.split("<!--")
       another=lst[1]
       value=another.split("-->")
       fw.write("#"+value[0]+"\n")
   else :
       lst=line.split("</string>")
       another=lst[0]
       lst2=another.split(">")
       key=lst2[1]
       yet_another=lst2[0]
       lst3=yet_another.split('=')
       value=lst3[1][1:-1]
       #value=yet_another
       fw.write(value+"="+key+"\n")
       #print key,value
fo.close()
fw.close()
