package org.atoiks.games.framework;

import java.awt.Toolkit;
import java.awt.Graphics;
import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class Frame extends AbstractFrame<JFrame> {

    private final JPanel canvas = new JPanel() {

        private static final long serialVersionUID = 91727385L;

        @Override
        protected void paintComponent(final Graphics g) {
            super.paintComponent(g);
            sceneMgr.renderCurrentScene(g);
        }
    };

    private final JFrame frame;

    public Frame(FrameInfo info) {
        super(info.fps, new SceneManager(info.scenes));
        frame = new JFrame(info.titleName);

        frame.setContentPane(canvas);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(info.resizable);
        canvas.setPreferredSize(new Dimension(info.width, info.height));
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.addKeyListener(sceneMgr.keyboard());
    }

    @Override
    protected int getWidth() {
        return canvas.getPreferredSize().width;
    }

    @Override
    protected int getHeight() {
        return canvas.getPreferredSize().height;
    }

    @Override
    protected void renderGame() {
        canvas.repaint();
        Toolkit.getDefaultToolkit().sync();
    }

    @Override
    public void setSize(int width, int height) {
        canvas.setPreferredSize(new Dimension(width, height));
        frame.pack();
    }

    @Override
    public void setTitle(String title) {
        frame.setTitle(title);
    }

    @Override
    public JFrame getRawFrame() {
        return frame;
    }

    @Override
    public void init() {
        super.init();
        frame.setVisible(true);
    }

    @Override
    public void close() {
        super.close();
        frame.dispose();
    }
}