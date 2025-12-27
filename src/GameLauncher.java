import java.util.concurrent.CountDownLatch;

public class GameLauncher {
    public static void main(String[] args) throws InterruptedException {
        int playerCount = 5;
        // 1. 初始化计数器为5
        CountDownLatch latch = new CountDownLatch(playerCount);

        System.out.println("等待玩家进入房间...");

        for (int i = 1; i <= playerCount; i++) {
            final int playerId = i;
            new Thread(
                    () -> {
                        try {
                            // 模拟玩家准备动作
                            Thread.sleep((long) (Math.random() * 3000));
                            System.out.println("玩家 " + playerId + " 已就绪");
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        } finally {
                            latch.countDown();
                        }
                    }).start();
        }

        // 3. 主线程等待，直到计数器清零
        System.out.println("主裁判正在检查玩家状态...");
        latch.await();

        System.out.println(">>> 所有玩家就绪，游戏正式开始！");
    }
}
