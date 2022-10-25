package func;

import UI.Tags;
import burp.*;
import utils.BurpAnalyzedRequest;
import yaml.YamlUtil;

import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

public class vulscan {

    private IBurpExtenderCallbacks call;

    private BurpAnalyzedRequest Root_Request;

    private IExtensionHelpers help;
    public String Path_record;
    public BurpExtender burp;
    public IHttpService httpService;


    public vulscan(BurpExtender burp, BurpAnalyzedRequest Root_Request) {
        this.burp = burp;
        this.call = burp.call;
        this.help = burp.help;
        this.Path_record = "";
        this.Root_Request = Root_Request;
        // 获取httpService对象
        byte[] request = this.Root_Request.requestResponse().getRequest();
        httpService = this.Root_Request.requestResponse().getHttpService();
        IRequestInfo analyze_Request = help.analyzeRequest(httpService, request);
        List<String> heads = analyze_Request.getHeaders();
        burp.ThreadPool = Executors.newFixedThreadPool((Integer) burp.Config_l.spinner1.getValue());


        // 判断请求方法为POST
        if (this.help.analyzeRequest(request).getMethod() == "POST")
            //将POST切换为GET请求
            request = this.help.toggleRequestMethod(request);
        // 获取所有参数
        List<IParameter> Parameters = this.help.analyzeRequest(request).getParameters();
        // 判断参数列表不为空
        if (!Parameters.isEmpty())
            for (IParameter parameter : Parameters)
                // 删除所有参数
                request = this.help.removeParameter(request, parameter);
        // 创建新的请求类
//        IHttpRequestResponse newHttpRequestResponse = this.call.makeHttpRequest(httpService, request);
        IHttpRequestResponse newHttpRequestResponse = Root_Request.requestResponse();
        // 使用/分割路径
        String[] paths = this.help.analyzeRequest(newHttpRequestResponse).getUrl().getPath().split("/");

        Map<String, Object> Yaml_Map = YamlUtil.readYaml(burp.Config_l.yaml_path);
        List<Map<String, Object>> Listx = (List<Map<String, Object>>) Yaml_Map.get("Load_List");
        if (paths.length == 0) {
            paths = new String[]{""};
        }
        for (String path : paths) {
            if (path.contains(".") && path.equals(paths[paths.length - 1])) {
                    break;
            }

            if (!path.equals("")) {
                this.Path_record = this.Path_record + "/" + path;
            }

            for (Map<String, Object> zidian : Listx) {
                burp.ThreadPool.execute(new threads(zidian, this, newHttpRequestResponse, heads));
            }

            while (true) {
                // 防止线程混乱，睡眠0.3秒
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if (((ThreadPoolExecutor) burp.ThreadPool).getActiveCount() == 0) {
                    break;
                }
            }


        }


    }


    public static void ir_add(Tags tag, String title, String method, String url, String StatusCode, String notes, String Size, IHttpRequestResponse newHttpRequestResponse) {
        if (!tag.Get_URL_list().contains(url)) {
            tag.add(title, method, url, StatusCode, notes, Size, newHttpRequestResponse);
        }
    }


}

