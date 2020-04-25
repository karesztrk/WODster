import React, { Fragment } from 'react'

import PostsListItem from './PostsListItem'

class PostsList extends React.Component {
  render() {
    const { posts } = this.props;

    return (
      <Fragment>
        {posts.map(post => {
          const props = {
            title: post.node.frontmatter ? post.node.frontmatter.title : post.node.title,
            excerpt: post.node.excerpt,
            slug: post.node.frontmatter ? post.node.frontmatter.slug : post.node.slug,
            timeToRead: post.node.timeToRead,
            tags: post.node.frontmatter ? post.node.frontmatter.tags : [],
          };
          return <PostsListItem key={props.slug} {...props} />
        })}
      </Fragment>
    )
  }
}
export default PostsList
