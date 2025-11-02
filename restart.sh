#!/bin/bash
APP_NAME="entrance-0.0.1-SNAPSHOT.jar"
APP_PATH="/www/wwwroot/e-commerce/service"
PID_FILE="$APP_PATH/app.pid"
LOG_FILE="$APP_PATH/app.log"

cd $APP_PATH

# 如果进程存在则停止
if [ -f "$PID_FILE" ]; then
  PID=$(cat "$PID_FILE")
  if ps -p $PID > /dev/null 2>&1; then
    echo "🛑 正在停止 $APP_NAME (PID: $PID)..."
    kill -15 $PID
    sleep 3
  fi
  rm -f "$PID_FILE"
fi

# 启动新的实例
echo "🚀 正在启动 $APP_NAME..."
nohup java -jar $APP_PATH/$APP_NAME > $LOG_FILE 2>&1 &
echo $! > $PID_FILE
echo "✅ 启动成功，新进程 PID: $(cat $PID_FILE)"