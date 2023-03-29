const { createProxyMiddleware } = require('http-proxy-middleware');

module.exports = function(app) {
  app.use(
    '/api',
    createProxyMiddleware({
      target: 'http://138.251.179.46:8080',
      changeOrigin: true,
    })
  );
};
