#!/bin/bash
APP_NAME="project.jar"
APP_PATH="app_path"
PID_FILE="$APP_PATH/app.pid"
LOG_FILE="$APP_FILE/app.log"
PORT=0

cd $APP_PATH

# 通过PORT端口查找进程PID并停止
echo "🔍 正在查找占用端口 $PORT 的进程..."
PID=$(lsof -t -i:$PORT)
if [ -n "$PID" ]; then
  echo "🛑 正在停止进程 (PID: $PID)..."
  kill -15 "$PID"
  sleep 3

  # 强制杀死进程（如果还在运行）
  if ps -p "$PID" > /dev/null 2>&1; then
    echo "⚠️  强制终止进程..."
    kill -9 "$PID"
  fi
else
  echo "ℹ️ 占用端口 $PORT 的进程不存在"
fi

# 如果存在PID文件则删除
if [ -f "$PID_FILE" ]; then
  rm -f "$PID_FILE"
fi

# 启动新的实例
echo "🚀 正在启动 $APP_NAME..."
nohup java -jar $APP_PATH$APP_NAME --spring.profiles.active=prod > "$LOG_FILE" 2>&1 &
echo $! > $PID_FILE
echo "✅ 启动成功，新进程 PID: $(cat $PID_FILE)"
