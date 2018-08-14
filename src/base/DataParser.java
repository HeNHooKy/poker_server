package base;

import java.util.HashMap;
import java.util.Map;


public class DataParser {
    public static String stringify(String[] data)
    {//TODO(hanji): разобраться с парсером
        String str = "";
        for(int i = 0; i < data.length; i++)
        {
            data[i] = data[i].replace("/","\\");
            str += data[i] +
                    (data.length-1 == i ? "" : "/");
        }
        return str;
    }

    public static String stringify(Map<String,String> map)
    {
        String str = "";
        for(Map.Entry<String,String> entry : map.entrySet())
        {
            str += "/" + entry.getKey() + "/" + entry.getValue();
        }
        return str.replaceFirst("/","");
    }

    public static HashMap<String, String> parse(String data) throws ArithmeticException
    {
        HashMap<String, String> parseData = new HashMap<>();
        String[] args = data.split("/",0);
        if(args.length%2 != 0)
        {
            System.err.println("Can't to parse data; args.length is uneven");
            throw new ArithmeticException("Can't to parse data");
        }
        for(int i = 0; i < args.length; i+=2)
        {
            parseData.put(args[i],args[i+1]);
        }
        return parseData;
    }



    public static String[] mapParse(HashMap<String,String> map) {
        String[] args = new String[map.size()*2];
        int i = 0;
        for(Map.Entry<String,String> entry :
                map.entrySet())
        {
            args[i] = entry.getKey();
            args[i+1] = entry.getValue();
            i+=2;
        }
        return args;
    }

    public static boolean isCanParse(String data) {
        String[] args = data.split("/",0);

        return args.length%2 == 0;

    }
}
/*комментарии?*/
