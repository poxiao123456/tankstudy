package com.poxiao.tank.net.netty;

import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * @author qq
 * @date 2020/11/26
 */
public class ServerFrame extends Frame{

    public static final ServerFrame INSTANCE = new ServerFrame();

    Button btnStart = new Button("start");
    TextArea taLeft = new TextArea();
    TextArea taRight = new TextArea();
    Server server = new Server();
    Client client = new Client();

    public ServerFrame () {
        this.setSize(1600, 600);
        this.setLocation(300, 30);
        this.add(btnStart, BorderLayout.NORTH);
        Panel p = new Panel(new GridLayout(1, 2));
        p.add(taLeft);
        p.add(taRight);
        this.add(p);

        taLeft.setFont(new Font("verderna",Font.PLAIN, 25));
        taRight.setFont(new Font("verderna",Font.PLAIN, 25));

        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.out.println("服务器关闭");
                System.exit(0);
            }
        });


    }

    public static void main(String[] args) {
        ServerFrame.INSTANCE.setVisible(true);

        new Thread(() -> {
            ServerFrame.INSTANCE.server.serverStart();
        }).start();

        new Thread(() -> {
            ServerFrame.INSTANCE.client.connect();
        }).start();
    }

    public void updateServerMsg(String string) {
        this.taLeft.setText(taLeft.getText() + string + System.getProperty("line.separator"));
    }

    public void updateClientMsg(String string) {
        this.taRight.setText(taRight.getText() + string + System.getProperty("line.separator"));
    }
}
