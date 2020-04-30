package site.nerdygadgets.scraper;

public class Runner {
    public Runner() {
        Server web1 = new Server(1, ServerType.UBUNTU, "172.16.0.190", "student", "KHxd4gu7");
        web1.grabData();
        System.out.println(web1);
    }
}
