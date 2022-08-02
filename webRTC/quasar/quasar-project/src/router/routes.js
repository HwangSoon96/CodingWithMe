
const routes = [
  {
    path: '/',
    redirect : 'main',
  },

  {
		path: '/webRTC',
    component: () => import('../layouts/MainLayout.vue'),
	},

  {
		path: '/goldenLayout',
		component: () => import('../layouts/GridLayout.vue')
	},

  // Always leave this as last one,
  // but you can also remove it
  {
    path: '/:catchAll(.*)*',
    component: () => import('pages/ErrorNotFound.vue')
  }
]

export default routes
