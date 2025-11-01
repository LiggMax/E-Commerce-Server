#!/bin/bash
# ====================================================
# restart.sh - 优雅重启 Spring Boot 应用脚本（带端口检测）
# ====================================================

APP_NAME="entrance-0.0.1-SNAPSHOT.jar"
APP_DIR="/www/wwwroot/e-commerce/service"
LOG_FILE="$APP_DIR/app.log"
PID_FILE="$APP_DIR/app.pid"
PORT=8899   # 项目的端口

cd $APP_DIR

# 停止旧进程
if [ -f "$PID_FILE" ]; then
  OLD_PID=$(cat $PID_FILE)
  if ps -p $OLD_PID > /dev/null 2>&1; then
    echo "🟡 正在停止旧进程 (PID: $OLD_PID)..."
    kill -15 $OLD_PID
  fi
fi

# 等待端口释放
echo "⏳ 等待端口 $PORT 释放..."
for i in {1..15}; do
  if ! lsof -i:$PORT > /dev/null; then
    echo "✅ 端口 $PORT 已释放"
    break
  fi
  echo "⏱️ 等待中 ($i/15)"
  sleep 2
done

# 再次强制清理（防止残留）
if lsof -i:$PORT > /dev/null; then
  echo "⚠️ 端口仍被占用，强制清理..."
  fuser -k ${PORT}/tcp
fi

# 启动新进程
echo "🟢 启动新版本 $APP_NAME ..."
nohup java -jar $APP_DIR/$APP_NAME > $LOG_FILE 2>&1 &
NEW_PID=$!
echo $NEW_PID > $PID_FILE

echo "✅ 启动成功，PID=$NEW_PID"
