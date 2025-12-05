-- 初始化默认用户
INSERT INTO users (username, email, password, is_active, created_at, updated_at) VALUES 
('admin', 'admin@example.com', 'admin123', 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- 初始化一些示例地点数据
INSERT INTO location_geocodes (name, lng, lat, matched_name, confidence, created_at, updated_at) VALUES 
('北京', 116.4074, 39.9042, '北京', 'high', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('上海', 121.4737, 31.2304, '上海', 'high', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('杭州', 120.1551, 30.2741, '杭州', 'high', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);