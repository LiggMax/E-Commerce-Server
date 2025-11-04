#!/bin/bash
APP_NAME="project.jar"
APP_PATH="app_path"
PID_FILE="$APP_PATH/app.pid"
LOG_FILE="$APP_PATH/app.log"

cd $APP_PATH

# 通过APP_NAME查找进程PID并停止
echo "🔍 正在查找 $APP_NAME 进程..."
PID=$(ps -ef | grep "$APP_NAME" | grep -v grep | awk '{print $2}')
if [ -n "$PID" ]; then
  echo "🛑 正在停止 $APP_NAME (PID: $PID)..."
  kill -15 $PID
  sleep 3

  # 强制杀死进程（如果还在运行）
  if ps -p $PID > /dev/null 2>&1; then
    echo "⚠️  强制终止进程..."
    kill -9 $PID
  fi
else
  echo "ℹ️  未找到运行中的 $APP_NAME 进程"
fi

# 如果存在PID文件则删除
if [ -f "$PID_FILE" ]; then
  rm -f "$PID_FILE"
fi

# 启动新的实例
echo "🚀 正在启动 $APP_NAME..."
JAR_FILE=$(ls -t entrance*.jar | head -n 1)
nohup java -jar $JAR_FILE --spring.profiles.active=prod > $LOG_FILE 2>&1 &
echo $! > $PID_FILE
echo "✅ 启动成功，新进程 PID: $(cat $PID_FILE)"
