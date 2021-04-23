package main.netty.no;

/**
 * 主题
 */
public interface Subject {

   void registerObserver(Observer o); //注册
   void removeObserver(Observer o);// 移除
   void notifyObservers();
}
