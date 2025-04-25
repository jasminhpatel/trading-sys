INSERT INTO order_info (
    order_id, parent_order_id, source, account_id, client_order_id, exchange_id, exchange_order_id, instrument_type,
    batch_id, symbol, order_type, time_in_force, side, quantity, leaves_qty, cash_order_quantity, price, price_currency,
    stop_price, exec_type, order_status, transmission, created_at, updated_at, filled_at, cum_qty
) VALUES
(1001, NULL, 'EXTERNAL', 2001, 'EXT-ORD-001', 'NYSE', 'EXCH-001', 'EQUITY', 'BATCH-01', 'AAPL', 'MARKET', 'GTC', 'BUY', 100.0000, 0.0000, 0.00, 150.0000, 'USD', NULL, 'NEW', 'NEW', 'AT_EX_GATEWAY', NOW(), NOW(), NULL, 0.0000),
(1002, 1001, 'ALGO', 2002, 'ALGO-ORD-002', 'NASDAQ', 'EXCH-002', 'CRYPTO', 'BATCH-02', 'BTC/USD', 'LIMIT', 'IOC', 'SELL', 0.5000, 0.1000, 0.00, 30000.0000, 'USD', 29500.0000, 'TRADE', 'PARTIALLY_FILLED', 'AT_ALGO', NOW(), NOW(), NOW(), 0.4000);
