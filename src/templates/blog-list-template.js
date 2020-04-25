import React from 'react'
import { graphql } from 'gatsby'

import Layout from '../components/layout'
import Wrapper from '../components/Wrapper'
import Hero from '../components/Hero'
import PostsList from '../components/PostsList'
import Pagination from '../components/Pagination'
import SEO from '../components/SEO'

class BlogList extends React.Component {
  render() {
    const { data: { site: { siteMetadata }, posts: { edges }}, pageContext } = this.props;
    const { title, description } = siteMetadata;
    const posts = edges;

    return (
      <Layout location={this.props.location}>
        <SEO />
        <Hero title={title} subTitle={description} />

        <Wrapper>
          <PostsList posts={posts} />
        </Wrapper>

        <Pagination
          nbPages={pageContext.nbPages}
          currentPage={pageContext.currentPage}
        />
      </Layout>
    )
  }
}

export default BlogList

export const pageQuery = graphql`
  query blogListQuery($skip: Int!, $limit: Int!) {
    site {
      siteMetadata {
        title
        description
      }
    }
    posts: allPost (
      sort: { fields: date, order: DESC }
      filter: {slug: {ne: null}}
      limit: $limit
      skip: $skip
    ) {
      edges {
        node {
          slug
          excerpt
          date
          title
        }
      }
    }
  }
`;
