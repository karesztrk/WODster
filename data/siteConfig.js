module.exports = {
  siteTitle: 'No Failure, Only Feedback',
  siteDescription: '',
  authorName: 'Karoly Torok',
  authorAvatar: 'avatar.jpg', // file in content/images
  defaultLang: 'en', // show flag if lang is not default. Leave empty to enable flags in post lists
  authorDescription: `
  Workout addict , Software engineer. On his day to day job, he is working as a Software Engineer 👨‍💻. He is also doing fitness on a daily basis 🏃‍🏋️🏊‍.
  You need help with coding or do you want to know more? <a href="https://upbeat-brattain-c51372.netlify.app/" target="_blank">Visit my website!</a>
  `,
  //siteUrl: '',
  // Prefixes all links. For cases when deployed to maxpou.fr/gatsby-starter-morning-dew/
  pathPrefix: '', // Note: it must *not* have a trailing slash.
  siteCover: 'cover.jpg', // file in content/images
  background_color: '#ffffff',
  theme_color: '#222222',
  display: 'standalone',
  icon: 'content/images/rocket.svg',
  postsPerPage: 6,
  headerTitle: 'WODster',
  headerLinksIcon: '',
  headerLinks: [
    {
      label: 'Blog',
      url: '/',
    },
    {
      label: 'About',
      url: '/about',
    },
  ],
  // Footer information (ex: Github, Netlify...)
  websiteHost: {
    name: 'Netlify',
    url: 'https://www.netlify.com/',
  },
  footerLinks: [
    {
      sectionName: 'Explore',
      links: [
        {
          label: 'Blog',
          url: '/',
        },
        {
          label: 'About',
          url: '/about',
        },
      ],
    },
    {
      sectionName: 'Follow the author',
      links: [
        {
          label: 'GitHub',
          url: 'https://github.com/karesztrk',
        },
      ],
    },
  ],
};
