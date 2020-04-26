const { createFilePath } = require('gatsby-source-filesystem');

exports.createPages = async ({ graphql, actions, reporter }) => {
  const { createPage } = actions;

  const BlogPostTemplate = require.resolve('./src/templates/blog-post.js');
  const TrainingPostTemplate = require.resolve('./src/templates/training-post.js');
  const BlogPostShareImage = require.resolve(
    './src/templates/blog-post-share-image.js'
  );
  const PageTemplate = require.resolve('./src/templates/page.js');
  const PostsBytagTemplate = require.resolve('./src/templates/tags.js');
  const ListPostsTemplate = require.resolve(
    './src/templates/blog-list-template.js'
  );

  const allTrainingPostQuery = await graphql(`
    {
      allTrainingJson {
        edges {
          node {
            id
            message
            fields {
              slug
            }
          }
        }
      }
    }
  `);
  const { data: { allTrainingJson: { edges: allTrainingPostEdges } } } = allTrainingPostQuery;

  const trainingPosts = allTrainingPostEdges
    .filter(({ node }) => node.fields.slug);

  const allMarkdownQuery = await graphql(`
    {
      allMarkdown: allMarkdownRemark(
        sort: { fields: [frontmatter___date], order: DESC }
        filter: { frontmatter: { published: { ne: false } } }
        limit: 1000
      ) {
        edges {
          node {
            fileAbsolutePath
            frontmatter {
              title
              slug
              tags
              language
              cover {
                publicURL
              }
            }
            timeToRead
            excerpt
          }
        }
      }
    }
  `);

  if (allMarkdownQuery.errors) {
    reporter.panic(allMarkdownQuery.errors)
  }

  const postPerPageQuery = await graphql(`
    {
      site {
        siteMetadata {
          postsPerPage
        }
      }
    }
  `);

  const markdownFiles = allMarkdownQuery.data.allMarkdown.edges;

  const posts = markdownFiles.filter(item =>
    item.node.fileAbsolutePath.includes('/content/posts/')
  );

  // generate paginated post list
  const postsPerPage = postPerPageQuery.data.site.siteMetadata.postsPerPage;
  const nbPages = Math.ceil(posts.length + trainingPosts.length / postsPerPage);

  Array.from({ length: nbPages }).forEach((_, i) => {
    createPage({
      path: i === 0 ? `/` : `/pages/${i + 1}`,
      component: ListPostsTemplate,
      context: {
        limit: postsPerPage,
        skip: i * postsPerPage,
        currentPage: i + 1,
        nbPages: nbPages,
      },
    })
  });

  trainingPosts.forEach(({ node }) => {
    const { id, fields: { slug } } = node;
    createPage({
      path: slug,
      component: TrainingPostTemplate,
      context: {
        id,
        previous: 0,
        next: 0,
      },
    })
  });

  // generate blog posts
  posts.forEach((post, index, posts) => {
    const previous = index === posts.length - 1 ? null : posts[index + 1].node;
    const next = index === 0 ? null : posts[index - 1].node;
    createPage({
      path: post.node.frontmatter.slug,
      component: BlogPostTemplate,
      context: {
        slug: post.node.frontmatter.slug,
        previous,
        next,
      },
    });

    // generate post share images (dev only)
    if (process.env.gatsby_executing_command.includes('develop')) {
      createPage({
        path: `${post.node.frontmatter.slug}/image_share`,
        component: BlogPostShareImage,
        context: {
          slug: post.node.frontmatter.slug,
          width: 440,
          height: 220,
        },
      })
    }
  });

  // generate pages
  markdownFiles
    .filter(item => item.node.fileAbsolutePath.includes('/content/pages/'))
    .forEach(page => {
      createPage({
        path: page.node.frontmatter.slug,
        component: PageTemplate,
        context: {
          slug: page.node.frontmatter.slug,
        },
      })
    });

  // generate tags
  markdownFiles
    .filter(item => item.node.frontmatter.tags !== null)
    .reduce(
      (acc, cur) => [...new Set([...acc, ...cur.node.frontmatter.tags])],
      []
    )
    .forEach(uniqTag => {
      createPage({
        path: `tags/${uniqTag}`,
        component: PostsBytagTemplate,
        context: {
          tag: uniqTag,
        },
      })
    })
};

exports.onCreateNode = ({ node, actions, getNode, createNodeId, createContentDigest }) => {
  const { createNode, createNodeField, createParentChildLink } = actions;

  if (node.internal.type === `MarkdownRemark`) {
    const { frontmatter } = node;
    const { date, title, slug, unlisted, published } = frontmatter;
    const value = createFilePath({ node, getNode });

    if (value) {
      const postNode = {
        id: createNodeId(`${node.id} >>> MD`),
        children: [],
        parent: node.id,
        internal: {
          contentDigest: createContentDigest(node),
          type: 'Post',
        },
        excerpt: '',
        date,
        slug,
        title,
        unlisted,
        published,
      };
      createNode(postNode);
      createParentChildLink({ parent: node, child: postNode })
    }

    createNodeField({
      name: `slug`,
      node,
      value,
    })
  } else if (node.internal.type === `TrainingJson`) {
    const { message, created_time: date } = node;
    const match = message && message.match(/WEEK[0-9]+/g);
    const value = match ? match[0] : '';

    if (value) {
      const postNode = {
        id: createNodeId(`${node.id} >>> JSON`),
        children: [],
        parent: node.id,
        internal: {
          contentDigest: createContentDigest(node),
          type: 'Post',
        },
        excerpt: message ? `${message.substring(0, Math.min(message.length, 140))}...` : '',
        date,
        slug: value,
        title: value,
        unlisted: false,
        published: true,
      };
      createNode(postNode);
      createParentChildLink({ parent: node, child: postNode })
    }

    createNodeField({
      node,
      name: `slug`,
      value
    });
  }
};
