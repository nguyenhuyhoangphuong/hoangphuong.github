using System;
using System.Collections.Generic;
using System.Data;
using System.Data.Entity;
using System.Data.Entity.Infrastructure;
using System.Linq;
using System.Net;
using System.Net.Http;
using System.Web.Http;
using System.Web.Http.Description;
using csdl.Models;

namespace csdl.Controllers.API
{
    public class san_phamController : ApiController
    {
        private csdl1Entities db = new csdl1Entities();
       
        // GET: api/san_pham
        [HttpGet]
        [Route("api/san_pham/getall")]
        public IQueryable<san_pham> GetSan_Phams()
        {
            return db.san_pham;
        }

        [Route("api/san_pham/getall_dm/{id}")]
        public IHttpActionResult Getall_dm(int id)
        {
            var san_pham = db.san_pham.Where(x => x.iddm == id);
            if (!san_pham.Any())
            {
                return NotFound();
            }
            return Ok(san_pham);

        }

        [HttpGet]
        [Route("api/san_pham/tksanpham")]
        public int thongkesp()
        {
            
            return db.san_pham.Count();
        }

        [HttpGet]
        [Route("api/san_pham/getchitiet/{id}")]
        public IHttpActionResult getchitiet(int id)
        {
            san_pham san_pham = db.san_pham.Find(id);
            if (san_pham == null)
            {
                return NotFound();
            }

            return Ok(san_pham);
        }
        [HttpGet]
        [Route("api/san_pham/timkiem/{id}")]
        public IHttpActionResult gettimkiem(string id)
        {
            var san_pham = db.san_pham.Where(x => x.tensp.StartsWith(id.ToLower()));
            if (!san_pham.Any())
            {
                return NotFound();
            }
            return Ok(san_pham);
        }

        [HttpPut]
        [Route("api/sanpham/Putsan_pham/{id}")]
        public IHttpActionResult Putsan_pham(int id, san_pham san_pham)
        {
            if (!ModelState.IsValid)
            {
                return BadRequest(ModelState);
            }

            if (id != san_pham.idsp)
            {
                return BadRequest();
            }

            db.Entry(san_pham).State = EntityState.Modified;

            try
            {
                db.SaveChanges();
            }
            catch (DbUpdateConcurrencyException)
            {
                if (!san_phamExists(id))
                {
                    return NotFound();
                }
                else
                {
                    throw;
                }
            }

            return StatusCode(HttpStatusCode.NoContent);
        }

        // POST: api/san_pham
        [HttpPost]
        [Route("api/sanpham/Postsan_pham")]
        public IHttpActionResult Postsan_pham(san_pham san_pham)
        {
            if (!ModelState.IsValid)
            {
                return BadRequest(ModelState);
            }

            db.san_pham.Add(san_pham);
            db.SaveChanges();

            return CreatedAtRoute("DefaultApi", new { id = san_pham.idsp }, san_pham);
        }

        [HttpDelete]
        [Route("api/sanpham/Deletesan_pham/{id}")]
        public IHttpActionResult Deletesan_pham(int id)
        {
            san_pham san_pham = db.san_pham.Find(id);
            if (san_pham == null)
            {
                return NotFound();
            }

            db.san_pham.Remove(san_pham);
            db.SaveChanges();

            return Ok(san_pham);
        }

        protected override void Dispose(bool disposing)
        {
            if (disposing)
            {
                db.Dispose();
            }
            base.Dispose(disposing);
        }

        private bool san_phamExists(int id)
        {
            return db.san_pham.Count(e => e.idsp == id) > 0;
        }
    }
}